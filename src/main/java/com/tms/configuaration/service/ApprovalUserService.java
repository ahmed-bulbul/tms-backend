package com.tms.configuaration.service;


import com.tms.configuaration.repository.ApprovalUserRepository;
import com.tms.common.generics.AbstractRepository;
import com.tms.common.generics.AbstractService;
import com.tms.configuaration.entity.ApprovalUser;
import com.tms.configuaration.payload.request.ApprovalUserDto;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ApprovalUserService extends AbstractService<ApprovalUser, ApprovalUserDto> {

    private final ApprovalUserRepository approvalUserRepository;

    public ApprovalUserService(AbstractRepository<ApprovalUser> repository, ApprovalUserRepository approvalUserRepository) {
        super(repository);
        this.approvalUserRepository = approvalUserRepository;
    }

    @Override
    protected <T> T convertToResponseDto(ApprovalUser approvalUser) {
        return null;
    }

    @Override
    protected ApprovalUser convertToEntity(ApprovalUserDto approvalUserDto) {
        return null;
    }

    @Override
    protected ApprovalUser updateEntity(ApprovalUserDto dto, ApprovalUser entity) {
        return null;
    }

    public Set<ApprovalUser> findAllExistingApprovalUsers(Long approvalSettingId, Set<Long> userIds) {
        return approvalUserRepository.findAllByApprovalSettingIdAndUserIdInAndIsActiveTrue(approvalSettingId, userIds);
    }
}
