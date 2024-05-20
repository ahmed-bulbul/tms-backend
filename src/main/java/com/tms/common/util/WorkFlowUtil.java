package com.tms.common.util;

import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import com.tms.common.service.UserService;
import com.tms.configuaration.entity.ApprovalSetting;
import com.tms.configuaration.service.ApprovalSettingService;
import com.tms.configuaration.service.ApprovalUserService;
import com.tms.configuaration.service.WorkFlowActionService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class WorkFlowUtil {
    private final ApprovalSettingService approvalSettingService;
    private final WorkFlowActionService workFlowActionService;
    private final UserService userService;
    private final Helper helper;
    private final ApprovalUserService approvalUserService;


    public WorkFlowUtil(ApprovalSettingService approvalSettingService, WorkFlowActionService workFlowActionService,
                        UserService userService, Helper helper, ApprovalUserService approvalUserService) {
        this.approvalSettingService = approvalSettingService;
        this.workFlowActionService = workFlowActionService;
        this.userService = userService;
        this.helper = helper;
        this.approvalUserService = approvalUserService;
    }


    public void validateWorkflow(Long subModuleItemId, List<Long> workflowActionIds) {
        if (workflowActionIds.stream().noneMatch(workFlowActionId ->
                hasActionPerformPermission(subModuleItemId, workFlowActionId))) {
            throw AppServerException.badRequest(ErrorId.ACCESS_DENIED);
        }
    }
    public boolean hasActionPerformPermission(Long submoduleItemId, Long workflowActionId) {
        Optional<ApprovalSetting> approvalSetting = approvalSettingService
                .findByWorkFlowAndSubmoduleId(workflowActionId, submoduleItemId);


        return approvalSetting.isPresent() && CollectionUtils.isNotEmpty(approvalUserService
                .findAllExistingApprovalUsers(approvalSetting.get().getId(),
                        Collections.singleton(Helper.getAuthUserId())));
    }
}
