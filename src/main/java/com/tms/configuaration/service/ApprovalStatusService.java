package com.tms.configuaration.service;

import com.tms.common.generics.AbstractRepository;
import com.tms.common.generics.AbstractService;
import com.tms.common.util.Helper;
import com.tms.configuaration.entity.ApprovalStatus;
import com.tms.configuaration.payload.request.ApprovalStatusDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class ApprovalStatusService extends AbstractService<ApprovalStatus, ApprovalStatusDto> {
    public ApprovalStatusService(AbstractRepository<ApprovalStatus> repository) {
        super(repository);
    }

    @Override
    protected <T> T convertToResponseDto(ApprovalStatus approvalStatus) {
        return null;
    }

    @Override
    protected ApprovalStatus convertToEntity(ApprovalStatusDto approvalStatusDto) {
        ApprovalStatus approvalStatus = new ApprovalStatus();
        BeanUtils.copyProperties(approvalStatusDto, approvalStatus);
        approvalStatus.setUpdatedBy(Helper.getAuthUserId());
        return approvalStatus;
    }

    @Override
    protected ApprovalStatus updateEntity(ApprovalStatusDto dto, ApprovalStatus entity) {
        return null;
    }
}
