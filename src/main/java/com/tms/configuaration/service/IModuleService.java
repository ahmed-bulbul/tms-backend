package com.tms.configuaration.service;


import com.tms.common.payload.response.PageData;
import com.tms.configuaration.entity.ConfigModule;
import com.tms.configuaration.payload.response.ConfigModuleResponseDto;
import com.tms.configuaration.payload.response.ModuleViewModel;
import com.tms.configuaration.payload.search.ConfigMenuSearchDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IModuleService {

    void updateActiveStatus(Long id, Boolean isActive);

    List<ModuleViewModel> getAllModule();

    List<ConfigModule> saveItemList(List<ConfigModule> entityList);

    PageData search(ConfigMenuSearchDto searchDto, Pageable pageable);

    ConfigModuleResponseDto getSingle(Long id);

    ConfigModule findByIdUnfiltered(Long moduleId);
}
