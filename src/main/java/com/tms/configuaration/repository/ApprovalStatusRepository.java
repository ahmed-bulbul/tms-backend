package com.tms.configuaration.repository;


import com.tms.common.generics.AbstractRepository;
import com.tms.configuaration.constant.ApprovalStatusType;
import com.tms.configuaration.entity.ApprovalStatus;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ApprovalStatusRepository extends AbstractRepository<ApprovalStatus> {
    Set<ApprovalStatus> findByApprovalStatusTypeAndParentIdInAndIsActiveTrue(ApprovalStatusType statusType, Set<Long> parentIds);

    Set<ApprovalStatus> findByApprovalStatusTypeAndParentIdAndWorkFlowActionIdAndIsActiveTrue(ApprovalStatusType type, Long parentId,
                                                                                              Long workFlowActionId);

    Iterable<ApprovalStatus> findByApprovalStatusTypeAndParentIdAndWorkFlowActionIdNotAndIsActiveTrue(ApprovalStatusType type, Long parentId, Long initialActionId);

    void deleteAllByParentIdAndApprovalStatusType(Long parentId, ApprovalStatusType approvalStatusType);
}
