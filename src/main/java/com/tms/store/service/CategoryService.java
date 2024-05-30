package com.tms.store.service;

import com.tms.common.generics.AbstractRepository;
import com.tms.common.generics.AbstractSearchService;
import com.tms.common.util.Helper;
import com.tms.common.util.WorkFlowUtil;
import com.tms.configuaration.service.ApprovalStatusService;
import com.tms.configuaration.service.WorkFlowActionService;
import com.tms.store.dto.request.LocationRequestDto;
import com.tms.store.dto.search.LocationSearchDto;
import com.tms.store.entity.Location;
import com.tms.store.repository.LocationRepository;
import org.springframework.data.jpa.domain.Specification;

public class CategoryService extends AbstractSearchService<Location, LocationRequestDto, LocationSearchDto> {

    private final WorkFlowActionService workFlowActionService;
    private final WorkFlowUtil workFlowUtil;
    private final Helper helper;
    private final ApprovalStatusService approvalStatusService;

    private final LocationRepository locationRepository;

    public CategoryService(AbstractRepository<Location> repository, WorkFlowActionService workFlowActionService,
                           WorkFlowUtil workFlowUtil, Helper helper, ApprovalStatusService approvalStatusService, LocationRepository locationRepository) {
        super(repository);
        this.workFlowActionService = workFlowActionService;
        this.workFlowUtil = workFlowUtil;
        this.helper = helper;
        this.approvalStatusService = approvalStatusService;
        this.locationRepository = locationRepository;
    }

    @java.lang.Override
    protected Specification<Location> buildSpecification(LocationSearchDto searchDto) {
        return null;
    }

    @java.lang.Override
    protected <T> T convertToResponseDto(Location location) {
        return null;
    }

    @java.lang.Override
    protected Location convertToEntity(LocationRequestDto locationRequestDto) {
        return null;
    }

    @java.lang.Override
    protected Location updateEntity(LocationRequestDto dto, Location entity) {
        return null;
    }
}
