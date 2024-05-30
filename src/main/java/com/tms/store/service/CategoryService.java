package com.tms.store.service;

import com.tms.common.generics.AbstractRepository;
import com.tms.common.generics.AbstractSearchService;
import com.tms.common.util.Helper;
import com.tms.common.util.WorkFlowUtil;
import com.tms.configuaration.service.ApprovalStatusService;
import com.tms.configuaration.service.WorkFlowActionService;
import com.tms.projects.dto.CategoryRequestDto;
import com.tms.projects.dto.CategorySearchDto;
import com.tms.store.entity.Category;
import com.tms.store.repository.LocationRepository;
import org.springframework.data.jpa.domain.Specification;

public class CategoryService extends AbstractSearchService<Category, CategoryRequestDto, CategorySearchDto> {

    private final WorkFlowActionService workFlowActionService;
    private final WorkFlowUtil workFlowUtil;
    private final Helper helper;
    private final ApprovalStatusService approvalStatusService;

    private final LocationRepository locationRepository;

    public CategoryService(AbstractRepository<Category> repository, WorkFlowActionService workFlowActionService,
                           WorkFlowUtil workFlowUtil, Helper helper, ApprovalStatusService approvalStatusService, LocationRepository locationRepository) {
        super(repository);
        this.workFlowActionService = workFlowActionService;
        this.workFlowUtil = workFlowUtil;
        this.helper = helper;
        this.approvalStatusService = approvalStatusService;
        this.locationRepository = locationRepository;
    }


    @Override
    protected Specification<Category> buildSpecification(CategorySearchDto searchDto) {
        return null;
    }

    @Override
    protected <T> T convertToResponseDto(Category category) {
        return null;
    }

    @Override
    protected Category convertToEntity(CategoryRequestDto categoryRequestDto) {
        return null;
    }

    @Override
    protected Category updateEntity(CategoryRequestDto dto, Category entity) {
        return null;
    }
}
