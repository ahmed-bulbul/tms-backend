package com.tms.configuaration.service;


import com.tms.configuaration.repository.ConfigSubModuleRepository;
import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import com.tms.common.payload.response.PageData;
import com.tms.common.util.Helper;
import com.tms.configuaration.entity.ConfigSubModule;
import com.tms.configuaration.payload.response.ConfigSubModuleResponseDto;
import com.tms.configuaration.payload.search.ConfigMenuSearchDto;
import org.springframework.context.annotation.Lazy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ConfigSubModuleService implements ISubModuleService{

    private final ConfigSubModuleRepository subModuleRepository;
    private final IModuleService moduleService;
    private final ISubModuleItemService itemService;

    protected static final Logger LOGGER = LoggerFactory.getLogger(ConfigSubModuleService.class);

    public ConfigSubModuleService(ConfigSubModuleRepository subModuleRepository, IModuleService moduleService, @Lazy ISubModuleItemService itemService) {
        this.subModuleRepository = subModuleRepository;
        this.moduleService = moduleService;
        this.itemService = itemService;
    }

    @Override
    public void updateActiveStatus(Long id, Boolean isActive) {

    }

    @Override
    public PageData search(ConfigMenuSearchDto searchDto, Pageable pageable) {
        return null;
    }

    @Override
    public ConfigSubModuleResponseDto getSingle(Long id) {
        return null;
    }

    @Override
    public List<ConfigSubModule> saveItemList(List<ConfigSubModule> entityList) {
        try {
            if (CollectionUtils.isEmpty(entityList)) {
                return entityList;
            }
//            if (subModuleRepository.findById(entityList.get(FIRST_INDEX).getId()).isPresent()) {
//                return Collections.emptyList();
//            }
            return subModuleRepository.saveAll(entityList);
        } catch (Exception e) {
            String entityName = entityList.get(0).getClass().getSimpleName();
            LOGGER.error("Save failed for entity {}", entityName);
            LOGGER.error("Error message: {}", e.getMessage());
            throw AppServerException.dataSaveException(
                    Helper.createDynamicCode(ErrorId.DATA_NOT_SAVED_DYNAMIC,
                            entityName));
        }
    }

    @Override
    public boolean isModuleInActivePossible(Long id) {
        return false;
    }

    @Override
    public ConfigSubModule findByIdUnfiltered(Long subModuleId) {
        return null;
    }
}
