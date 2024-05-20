package com.tms.common.service.impl;

import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import com.tms.common.models.Organization;
import com.tms.common.repository.OrganizationRepository;
import com.tms.common.service.OrganizationService;
import com.tms.common.util.Helper;
import com.tms.configuaration.payload.response.ModuleViewModel;
import com.tms.configuaration.service.ConfigModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
public class OrganizationServiceImpl implements OrganizationService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ConfigModuleService.class);

    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public void updateActiveStatus(Long id, Boolean isActive) {

    }

    @Override
    public List<ModuleViewModel> getAllModule() {
        return null;
    }

    @Override
    public List<Organization> saveItemList(List<Organization> entityList) {
        try {
            if (CollectionUtils.isEmpty(entityList)) {
                return entityList;
            }
            return organizationRepository.saveAll(entityList);
        } catch (Exception e) {
            String entityName = entityList.get(0).getClass().getSimpleName();
            LOGGER.error("Save failed for entity {}", entityName);
            LOGGER.error("Error message: {}", e.getMessage());
            throw AppServerException.dataSaveException(Helper.createDynamicCode(ErrorId.DATA_NOT_SAVED_DYNAMIC,
                    entityName));
        }
    }


    @Override
    public Organization findByIdUnfiltered(Long orgId) {
        return null;
    }

    @Override
    public Organization findById(Long orgId) {
        return organizationRepository.findById(orgId).orElse(null);
    }


}
