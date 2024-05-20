package com.tms.configuaration.repository;


import com.tms.common.loader.SubModuleJsonLoader;
import com.tms.common.util.CustomPair;
import com.tms.configuaration.entity.ApprovalSetting;
import com.tms.configuaration.entity.WorkFlowAction;
import com.tms.configuaration.service.WorkFlowActionService;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class ApprovalSettingEntityMangerRepo {
    private final EntityManager entityManager;
    private final WorkFlowActionService workFlowActionService;

    protected ApprovalSettingEntityMangerRepo(EntityManager entityManager, WorkFlowActionService workFlowActionService) {
        this.entityManager = entityManager;
        this.workFlowActionService = workFlowActionService;
    }

    public void deleteAllPendingApprovals(ApprovalSetting approvalSetting) {
        Long subModuleItemId = approvalSetting.getSubModuleItemId();
        CustomPair<String, Boolean> pair = SubModuleJsonLoader.getTableSmiMap().get(subModuleItemId);
        String tableName = pair.getKey();
        WorkFlowAction navigatedAction = workFlowActionService.getNavigatedAction(true, approvalSetting.getWorkFlowAction(), subModuleItemId);
        String q = "update " + tableName + " set workflow_action_id = " + navigatedAction.getId() + " where " +
                " workflow_action_id = " + approvalSetting.getWorkFlowActionId();
        if (pair.getValue() == Boolean.TRUE) {
            q += " AND submodule_item_id = " + approvalSetting.getSubModuleItemId();
        }
        Query nativeQuery = entityManager.createNativeQuery(q);
        nativeQuery.executeUpdate();
    }

}
