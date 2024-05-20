package com.tms.configuaration.service;

import com.tms.configuaration.payload.response.*;
import com.tms.configuaration.repository.ConfigModuleRepository;
import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import com.tms.common.models.AccessRight;
import com.tms.common.payload.response.PageData;
import com.tms.common.util.Helper;
import com.tms.configuaration.entity.ConfigModule;
import com.tms.configuaration.entity.ConfigSubModule;
import com.tms.configuaration.entity.ConfigSubmoduleItem;
import com.tms.configuaration.payload.search.ConfigMenuSearchDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class ConfigModuleService implements IModuleService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ConfigModuleService.class);
    private final ConfigModuleRepository moduleRepository;

    public ConfigModuleService(ConfigModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    //    private final ISubModuleService subModuleService;
    @Override
    public void updateActiveStatus(Long id, Boolean isActive) {

    }

    @Override
    public List<ModuleViewModel> getAllModule() {
        List<ConfigModule> moduleList = moduleRepository.findAllByIsActiveTrue();
        List<ModuleViewModel> moduleViewModelList = new ArrayList<>();
        for (ConfigModule module : moduleList) {
            ModuleViewModel moduleViewModel = new ModuleViewModel();
            moduleViewModel.setModuleId(module.getId());
            moduleViewModel.setModuleName(module.getModuleName());
            moduleViewModel.setImage(module.getImage());
            module.setOrder(module.getOrder());

            List<ConfigSubModule> subModuleList = module.getSubModuleList();
            List<SubModuleViewModel> subModuleViewModelList = new ArrayList<>();
            for (ConfigSubModule subModule : subModuleList) {
                SubModuleViewModel subModuleViewModel = new SubModuleViewModel();
                subModuleViewModel.setSubModuleId(subModule.getId());
                subModuleViewModel.setSubModuleName(subModule.getSubmoduleName());
                subModuleViewModel.setOrder(subModule.getOrder());

                List<ConfigSubmoduleItem> featureList = subModule.getSubmoduleItems();
                List<FeatureViewModel> featureViewModelList = new ArrayList<>();
                for (ConfigSubmoduleItem feature : featureList) {
                    FeatureViewModel featureViewModel = new FeatureViewModel();

                    featureViewModel.setFeatureId(feature.getId());
                    featureViewModel.setFeatureName(feature.getItemName());
                    featureViewModel.setOrder(feature.getOrder());
                    featureViewModel.setUrlPath(feature.getUrlPath());
                    featureViewModel.setIsBase(feature.getIsBase());

                    Set<AccessRight> accessRightList = feature.getAccessRightSet();
                    List<ActionViewModel> actionViewModelList = new ArrayList<>();

                    for (AccessRight accessRight : accessRightList) {
                        ActionViewModel actionViewModel = new ActionViewModel();
                        actionViewModel.setActionId(accessRight.getAction().getId());
                        actionViewModel.setActionName(accessRight.getAction().getActionName());
                        actionViewModel.setAccessRightId(accessRight.getId());
                        actionViewModelList.add(actionViewModel);
                    }

                    featureViewModel.setActionViewModelList(actionViewModelList);
                    featureViewModelList.add(featureViewModel);
                }

                subModuleViewModel.setFeatureViewModelList(featureViewModelList);
                subModuleViewModelList.add(subModuleViewModel);
            }

            moduleViewModel.setSubModuleList(subModuleViewModelList);
            moduleViewModelList.add(moduleViewModel);
        }

        return moduleViewModelList;
    }

    @Override
    public List<ConfigModule> saveItemList(List<ConfigModule> entityList) {
        try {
            if (CollectionUtils.isEmpty(entityList)) {
                return entityList;
            }
            return moduleRepository.saveAll(entityList);
        } catch (Exception e) {
            String entityName = entityList.get(0).getClass().getSimpleName();
            LOGGER.error("Save failed for entity {}", entityName);
            LOGGER.error("Error message: {}", e.getMessage());
            throw AppServerException.dataSaveException(Helper.createDynamicCode(ErrorId.DATA_NOT_SAVED_DYNAMIC,
                    entityName));
        }
    }

    @Override
    public PageData search(ConfigMenuSearchDto searchDto, Pageable pageable) {
        return null;
    }

    @Override
    public ConfigModuleResponseDto getSingle(Long id) {
        return null;
    }

    @Override
    public ConfigModule findByIdUnfiltered(Long moduleId) {
        return null;
    }
}
