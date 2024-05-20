package com.tms.configuaration.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovalRequestDto {

    private String rejectedDesc;
    private String approvalDesc;
    @NotNull
    private Boolean approve;
}
