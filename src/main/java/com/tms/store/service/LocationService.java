package com.tms.store.service;


import com.tms.store.repository.LocationRepository;
import com.tms.common.constant.ApplicationConstant;
import com.tms.common.generics.AbstractRepository;
import com.tms.common.generics.AbstractSearchService;
import com.tms.common.generics.CustomSpecification;
import com.tms.common.payload.response.PageData;
import com.tms.common.util.Helper;
import com.tms.common.util.SortChanger;
import com.tms.common.util.WorkFlowUtil;
import com.tms.configuaration.entity.WorkFlowAction;
import com.tms.configuaration.payload.request.ApprovalRequestDto;
import com.tms.configuaration.payload.request.ApprovalStatusDto;
import com.tms.configuaration.service.ApprovalStatusService;
import com.tms.configuaration.service.WorkFlowActionService;
import com.tms.store.dto.request.LocationRequestDto;
import com.tms.store.dto.response.LocationResponseDto;
import com.tms.store.dto.search.LocationSearchDto;
import com.tms.store.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tms.common.constant.ApplicationConstant.INT_ONE;
import static com.tms.common.constant.ApplicationConstant.WORKFLOW_ACTION_ORDER.INITIAL_ORDER;
import static com.tms.configuaration.constant.ApprovalStatusType.STORE_DEMAND;

@Service
public class LocationService extends AbstractSearchService<Location, LocationRequestDto, LocationSearchDto> {

    private final WorkFlowActionService workFlowActionService;
    private final WorkFlowUtil workFlowUtil;
    private final Helper helper;
    private final ApprovalStatusService approvalStatusService;

    private final LocationRepository locationRepository;

    public static final String NAME = "name";

    public LocationService(AbstractRepository<Location> repository, WorkFlowActionService workFlowActionService,
                           WorkFlowUtil workFlowUtil, Helper helper, ApprovalStatusService approvalStatusService, LocationRepository locationRepository) {
        super(repository);
        this.workFlowActionService = workFlowActionService;
        this.workFlowUtil = workFlowUtil;
        this.helper = helper;
        this.approvalStatusService = approvalStatusService;
        this.locationRepository = locationRepository;
    }

    @Override
    protected Specification<Location> buildSpecification(LocationSearchDto searchDto) {
        return null;
    }

    @Override
    public Location create(LocationRequestDto locationRequestDto) {
        List<WorkFlowAction> sortedWorkflowActions = workFlowActionService.getSortedWorkflowActions(Sort.Direction.ASC);
        WorkFlowAction workFlowAction = workFlowActionService.getByIndex(INITIAL_ORDER, sortedWorkflowActions);
        workFlowUtil.validateWorkflow(helper.getSubModuleItemId(), Collections.singletonList(workFlowAction.getId()));

        Location location = convertToEntity(locationRequestDto);
        location.setWorkFlowAction(workFlowActionService.getByIndex(INITIAL_ORDER + INT_ONE, sortedWorkflowActions));
        Location entity = super.saveItem(location);

        approvalStatusService.create(ApprovalStatusDto.of(entity.getId(), STORE_DEMAND, workFlowAction));
        return entity;
    }

    @Override
    protected LocationResponseDto convertToResponseDto(Location location) {
        return LocationResponseDto.builder()
                .id(location.getId())
                .name(location.getName())
                .build();
    }

    @Override
    protected Location convertToEntity(LocationRequestDto locationRequestDto) {
        return Location.builder()
                .name(locationRequestDto.getName())
                .build();
    }

    @Override
    protected Location updateEntity(LocationRequestDto dto, Location entity) {
        return null;
    }


    @Override
    public PageData search(LocationSearchDto searchDto, Pageable pageable) {
        pageable = SortChanger.descendingSortByCreatedAt(pageable);
        Page<Location> pageData;
        pageData = customBuildSpecification(searchDto, pageable, null, null);
        return PageData.builder()
                .model(getResponseData(pageData.getContent()))
                .totalPages(pageData.getTotalPages())
                .totalElements(pageData.getTotalElements())
                .currentPage(pageable.getPageNumber() + 1)
                .build();
    }

    private List<LocationResponseDto> getResponseData(List<Location> locationList) {
        return locationList.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    private Page<Location> customBuildSpecification(LocationSearchDto searchDto, Pageable pageable, Set<Long> ids, Boolean isRejected) {

        CustomSpecification<Location> customSpecification = new CustomSpecification<>();

        Specification<Location> specification = Specification.where(
                customSpecification.equalSpecificationAtRoot(searchDto.getIsActive(), ApplicationConstant.IS_ACTIVE_FIELD)
                        .and(customSpecification.equalSpecificationAtRoot(isRejected, ApplicationConstant.IS_REJECTED_FIELD))
                        .and(customSpecification.equalSpecificationAtRoot(searchDto.getName(), NAME))

        );
        return locationRepository.findAll(specification,pageable);
    }

    public void makeDecision(Long id, ApprovalRequestDto approvalRequestDto) {

    }
}
