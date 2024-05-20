package com.tms.configuaration.service;

import com.tms.configuaration.repository.ApprovalSettingEntityMangerRepo;
import com.tms.configuaration.repository.ApprovalSettingRepository;
import com.tms.common.generics.AbstractSearchService;
import com.tms.configuaration.entity.ApprovalSetting;
import com.tms.configuaration.payload.request.ApprovalSettingDto;
import com.tms.configuaration.payload.search.ApprovalSettingSearchDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApprovalSettingService extends AbstractSearchService<ApprovalSetting, ApprovalSettingDto, ApprovalSettingSearchDto> {

    private final ApprovalSettingRepository approvalSettingRepository;
    private final ApprovalSettingEntityMangerRepo approvalSettingEntityManger;
    public ApprovalSettingService(ApprovalSettingRepository approvalSettingRepository, ApprovalSettingEntityMangerRepo approvalSettingEntityManger) {
        super(approvalSettingRepository);
        this.approvalSettingRepository = approvalSettingRepository;
        this.approvalSettingEntityManger = approvalSettingEntityManger;
    }

    @Override
    public ApprovalSetting create(ApprovalSettingDto approvalSettingDto) {
        // TODO
        return super.create(approvalSettingDto);
    }

    public List<ApprovalSetting> findBySubmoduleId(Long subModuleItemId) {
        return approvalSettingRepository.findBySubModuleItemIdAndIsActiveTrue(subModuleItemId);
    }

    public void makeAllInactiveByWfa(Long workflowActionId) {
        List<ApprovalSetting> approvalSettingsList = approvalSettingRepository.findByWorkFlowActionIdAndIsActiveTrue(workflowActionId);
        approvalSettingsList.forEach(approvalSetting -> {
            approvalSettingEntityManger.deleteAllPendingApprovals(approvalSetting);
            approvalSetting.setIsActive(false);
        });
        saveItemList(approvalSettingsList);
    }

    @Override
    protected Specification<ApprovalSetting> buildSpecification(ApprovalSettingSearchDto searchDto) {
        return null;
    }

    @Override
    protected <T> T convertToResponseDto(ApprovalSetting approvalSetting) {
        return null;
    }

    @Override
    protected ApprovalSetting convertToEntity(ApprovalSettingDto approvalSettingDto) {
        return null;
    }

    @Override
    protected ApprovalSetting updateEntity(ApprovalSettingDto dto, ApprovalSetting entity) {
        return null;
    }


    public Optional<ApprovalSetting> findByWorkFlowAndSubmoduleId(Long workFlowActionId, Long submoduleItemId) {
        return approvalSettingRepository
                .findByWorkFlowActionIdAndSubModuleItemIdAndIsActiveTrue(workFlowActionId, submoduleItemId);
    }
}
