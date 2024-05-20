package com.tms.configuaration.service;


import com.tms.common.payload.response.PageData;
import com.tms.configuaration.entity.ConfigSubmoduleItem;
import com.tms.configuaration.payload.response.ConfigSubmoduleItemResponseDto;
import com.tms.configuaration.payload.search.ConfigMenuSearchDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ISubModuleItemService {
    void updateActiveStatus(Long id, Boolean isActive);

    boolean isPossibleInActiveSubmodule(Long submoduleId);

    List<ConfigSubmoduleItem> getAllSubModuleItemsByIdIn(Set<Long> ids);

    ConfigSubmoduleItem findById(Long id);

    List<ConfigSubmoduleItem> saveItemList(List<ConfigSubmoduleItem> entityList);

    PageData search(ConfigMenuSearchDto searchDto, Pageable pageable);

    ConfigSubmoduleItemResponseDto getSingle(Long id);
}
