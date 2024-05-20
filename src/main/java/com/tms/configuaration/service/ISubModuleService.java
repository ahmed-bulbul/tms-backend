package com.tms.configuaration.service;


import com.tms.common.payload.response.PageData;
import com.tms.configuaration.entity.ConfigSubModule;
import com.tms.configuaration.payload.response.ConfigSubModuleResponseDto;
import com.tms.configuaration.payload.search.ConfigMenuSearchDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISubModuleService {
    void updateActiveStatus(Long id, Boolean isActive);

    PageData search(ConfigMenuSearchDto searchDto, Pageable pageable);

    ConfigSubModuleResponseDto getSingle(Long id);

    List<ConfigSubModule> saveItemList(List<ConfigSubModule> configSubModuleList);

    boolean isModuleInActivePossible(Long id);

    ConfigSubModule findByIdUnfiltered(Long subModuleId);
}
