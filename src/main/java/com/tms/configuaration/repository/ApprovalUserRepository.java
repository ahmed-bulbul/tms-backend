package com.tms.configuaration.repository;


import com.tms.common.generics.AbstractRepository;
import com.tms.configuaration.entity.ApprovalUser;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ApprovalUserRepository extends AbstractRepository<ApprovalUser> {

    Set<ApprovalUser> findAllByApprovalSettingIdAndUserIdInAndIsActiveTrue(Long approvalSettingId, Set<Long> userIds);
}
