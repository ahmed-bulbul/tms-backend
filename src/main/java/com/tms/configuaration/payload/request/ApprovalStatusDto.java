package com.tms.configuaration.payload.request;

import com.tms.configuaration.constant.ApprovalStatusType;
import com.tms.common.generics.IDto;
import com.tms.configuaration.entity.WorkFlowAction;
import lombok.Value;

@Value(staticConstructor = "of")
public class ApprovalStatusDto implements IDto {
    Long parentId;
    ApprovalStatusType approvalStatusType;
    WorkFlowAction workFlowAction;
}
