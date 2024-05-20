package com.tms.configuaration.payload.request;


import com.tms.common.constant.ErrorId;
import com.tms.common.generics.IDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalUserDto implements IDto {

    private Long id;
    @NotNull(message = ErrorId.APPROVAL_ITEM_ID_REQUIRED)
    private Long ApprovalSettingId;
    @NotNull(message = ErrorId.USER_ID_REQUIRED)
    private Long userId;
}
