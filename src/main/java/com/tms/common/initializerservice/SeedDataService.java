package com.tms.common.initializerservice;


import com.tms.common.models.Organization;
import com.tms.common.models.OrganizationTypeEnum;
import com.tms.common.service.OrganizationService;
import com.tms.configuaration.constant.ActionEnum;
import com.tms.configuaration.constant.ModuleEnum;
import com.tms.configuaration.constant.SubModuleEnum;
import com.tms.configuaration.constant.SubModuleItemEnum;
import com.tms.configuaration.entity.ConfigModule;
import com.tms.configuaration.entity.ConfigSubModule;
import com.tms.configuaration.entity.ConfigSubmoduleItem;
import com.tms.configuaration.service.IModuleService;
import com.tms.configuaration.service.ISubModuleItemService;
import com.tms.configuaration.service.ISubModuleService;
import com.tms.common.constant.ApplicationConstant;
import com.tms.common.loader.AccessRightJsonObject;
import com.tms.common.models.AccessRight;
import com.tms.common.models.Action;
import com.tms.common.service.AccessRightService;
import com.tms.common.service.ActionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SeedDataService {
    private final IModuleService moduleService;
    private final ISubModuleService subModuleService;
    private final ISubModuleItemService configSubmoduleItemService;
    private final ActionService actionService;
    private final AccessRightService accessRightService;

    private final OrganizationService organizationService;

    private final ObjectMapper objectMapper;
    private static final Integer ORDER_VALUE = 1;

    public SeedDataService(IModuleService moduleService,
                           ISubModuleService subModuleService,
                           ISubModuleItemService configSubmoduleItemService,
                           ActionService actionService,
                           @Lazy AccessRightService accessRightService,
                           OrganizationService organizationService, ObjectMapper objectMapper) {
        this.moduleService = moduleService;
        this.subModuleService = subModuleService;
        this.configSubmoduleItemService = configSubmoduleItemService;
        this.actionService = actionService;
        this.accessRightService = accessRightService;
        this.organizationService = organizationService;
        this.objectMapper = objectMapper;
    }

    @EventListener
    public void postConstruct(ApplicationStartedEvent event) {
        organizationService.saveItemList(buildOrganization(OrganizationTypeEnum.ROOT_ORG));
        moduleService.saveItemList(getConfigModuleList());
        subModuleService.saveItemList(getConfigSubModuleList());
        configSubmoduleItemService.saveItemList(getConfigSubModuleItemList());
        actionService.saveAll(getActions());
        accessRightService.saveAccessRightList(getAccessRights());
    }


    private List<ConfigModule> getConfigModuleList() {
        return Arrays.stream(ModuleEnum.values())
                .map(this::buildModule).collect(Collectors.toList());
    }
    private List<ConfigSubModule> getConfigSubModuleList() {
        return Arrays.stream(SubModuleEnum.values())
                .map(this::buildSubModule).collect(Collectors.toList());
    }
    private List<ConfigSubmoduleItem> getConfigSubModuleItemList() {
        return Arrays.stream(SubModuleItemEnum.values())
                .map(this::buildSubModuleItem).collect(Collectors.toList());
    }
    private List<Action> getActions() {
        return Arrays.stream(ActionEnum.values())
                .map(ActionEnum::toEntity)
                .collect(Collectors.toList());
    }
    private List<AccessRight> getAccessRights() {
        try {
            List<AccessRightJsonObject> accessRightJsonObjects = objectMapper
                    .readerForListOf(AccessRightJsonObject.class)
                    .readValue(new ClassPathResource(ApplicationConstant.ACCESS_RIGHTS_FILE_PATH).getFile());

            return convertJsonListToAccessRights(accessRightJsonObjects);
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private List<Organization> buildOrganization(OrganizationTypeEnum organizationTypeEnum) {
        return Arrays.asList(createRootOrg(organizationTypeEnum),
                createSubOrg(organizationTypeEnum));
    }

    private static Organization createRootOrg(OrganizationTypeEnum organizationTypeEnum) {
        System.out.println("ID::_"+OrganizationTypeEnum.ROOT_ORG.getOrgId());
        Organization organization = new Organization();
        organization.setId(OrganizationTypeEnum.ROOT_ORG.getOrgId());
        organization.setOrgCode("ATL");
        organization.setReference("ATL");
        organization.setOrganizationType(OrganizationTypeEnum.ROOT_ORG);
        organization.setName("ATL");
        organization.setContactName("ATL");
        return organization;
    }
    private static Organization createSubOrg(OrganizationTypeEnum organizationTypeEnum) {
        Organization organization = new Organization();
        organization.setId(OrganizationTypeEnum.SUB_ORG.getOrgId());
        organization.setOrgCode("ATL-1");
        organization.setReference("ATL-1");
        organization.setOrganizationType(OrganizationTypeEnum.SUB_ORG);
        organization.setName("ATL-1");
        organization.setContactName("ATL-1");
        return organization;
    }

    private ConfigModule buildModule(ModuleEnum moduleEnum) {
        ConfigModule module = new ConfigModule();
        module.setModuleName(moduleEnum.name());
        module.setOrder(moduleEnum.ordinal() + 1);
        module.setId(moduleEnum.getConfigModuleId());
        Organization org = organizationService.findById(OrganizationTypeEnum.ROOT_ORG.getOrgId());
        module.setOrganization(org);
        return module;
    }
    private ConfigSubModule buildSubModule(SubModuleEnum subModuleEnum) {
        ConfigSubModule configSubModule = new ConfigSubModule();
        configSubModule.setId(subModuleEnum.getSubModuleId());
        configSubModule.setSubmoduleName(subModuleEnum.name());
        configSubModule.setOrder(subModuleEnum.ordinal() + 1);
        configSubModule.setModule(ConfigModule.withId(subModuleEnum.getModule().getConfigModuleId()));
        return configSubModule;
    }
    private ConfigSubmoduleItem buildSubModuleItem(SubModuleItemEnum subModuleItemEnum) {
        ConfigSubmoduleItem configSubmoduleItem = new ConfigSubmoduleItem();
        configSubmoduleItem.setId(subModuleItemEnum.getSubModuleItemId());
        configSubmoduleItem.setItemName(subModuleItemEnum.name());
        configSubmoduleItem.setOrder(subModuleItemEnum.ordinal() + ORDER_VALUE);
        configSubmoduleItem.setSubModule(
                ConfigSubModule.withId(subModuleItemEnum.getSubModule().getSubModuleId()));
        return configSubmoduleItem;
    }

    private List<AccessRight> convertJsonListToAccessRights(List<AccessRightJsonObject> accessRightJsonObjects) {
        return accessRightJsonObjects.stream()
                .map(this::convertToAccessRight)
                .collect(Collectors.toList());
    }

    private AccessRight convertToAccessRight(AccessRightJsonObject jsonObject) {
        AccessRight accessRight = new AccessRight();
        accessRight.setId(jsonObject.getId());
        accessRight.setAction(Action.withId(jsonObject.getActionId()));
        accessRight.setConfigSubmoduleItem(ConfigSubmoduleItem.withId(jsonObject.getSubmoduleItemId().longValue()));
        accessRight.setRoleSet(new HashSet<>());
        return accessRight;
    }
}
