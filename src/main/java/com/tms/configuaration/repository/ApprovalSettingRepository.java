package com.tms.configuaration.repository;


import com.tms.common.generics.AbstractRepository;
import com.tms.configuaration.entity.ApprovalSetting;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalSettingRepository extends AbstractRepository<ApprovalSetting> {

    Optional<ApprovalSetting> findByWorkFlowActionIdAndSubModuleItemIdAndIsActiveTrue(Long workFlowAction, Long SubModuleId);

    List<ApprovalSetting> findBySubModuleItemIdAndIsActiveTrue(Long subModuleItemId);

    List<ApprovalSetting> findByWorkFlowActionIdAndIsActiveTrue(Long workflowActionId);
}
