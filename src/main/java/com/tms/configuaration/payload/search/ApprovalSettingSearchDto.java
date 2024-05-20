package com.tms.configuaration.payload.search;

import com.tms.common.generics.SDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalSettingSearchDto implements SDto {
    private String workFlowActionName;
    private String subModuleItemName;
    private Boolean isActive = true;
}
