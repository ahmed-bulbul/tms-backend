package com.tms.common.service;

import com.tms.common.models.Organization;
import com.tms.common.payload.response.PageData;
import com.tms.configuaration.entity.ConfigModule;
import com.tms.configuaration.payload.response.ConfigModuleResponseDto;
import com.tms.configuaration.payload.response.ModuleViewModel;
import com.tms.configuaration.payload.search.ConfigMenuSearchDto;
import com.tms.configuaration.service.IModuleService;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrganizationService  {

    void updateActiveStatus(Long id, Boolean isActive);

    List<ModuleViewModel> getAllModule();

    List<Organization> saveItemList(List<Organization> entityList);


    //PageData search(ConfigMenuSearchDto searchDto, Pageable pageable);

    //ConfigModuleResponseDto getSingle(Long id);

    Organization findByIdUnfiltered(Long orgId);

    Organization findById(Long orgId);
}
