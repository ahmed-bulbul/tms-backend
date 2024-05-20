package com.tms.configuaration.service;


import com.tms.configuaration.repository.ConfigSubmoduleItemRepository;
import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import com.tms.common.payload.response.PageData;
import com.tms.common.util.Helper;
import com.tms.configuaration.entity.ConfigSubmoduleItem;
import com.tms.configuaration.payload.response.ConfigSubmoduleItemResponseDto;
import com.tms.configuaration.payload.search.ConfigMenuSearchDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service
public class ConfigSubmoduleItemService implements ISubModuleItemService{

    private final ConfigSubmoduleItemRepository itemRepository;
    private final ISubModuleService subModuleService;
    protected static final Logger LOGGER = LoggerFactory.getLogger(ConfigSubmoduleItemService.class);

    public ConfigSubmoduleItemService(ConfigSubmoduleItemRepository itemRepository, ISubModuleService subModuleService) {
        this.itemRepository = itemRepository;
        this.subModuleService = subModuleService;
    }

    @Override
    public void updateActiveStatus(Long id, Boolean isActive) {

    }

    @Override
    public boolean isPossibleInActiveSubmodule(Long submoduleId) {
        return false;
    }

    @Override
    public List<ConfigSubmoduleItem> getAllSubModuleItemsByIdIn(Set<Long> ids) {
        return null;
    }

    @Override
    public ConfigSubmoduleItem findById(Long id) {
        return null;
    }

    @Override
    public List<ConfigSubmoduleItem> saveItemList(List<ConfigSubmoduleItem> entityList) {
        try {
            if (CollectionUtils.isEmpty(entityList)) {
                return entityList;
            }
//            if (itemRepository.findById(entityList.get(FIRST_INDEX).getId()).isPresent()) {
//                return Collections.emptyList();
//            }
            return itemRepository.saveAll(entityList);
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
    public PageData search(ConfigMenuSearchDto searchDto, Pageable pageable) {
        return null;
    }

    @Override
    public ConfigSubmoduleItemResponseDto getSingle(Long id) {
        return null;
    }
}
