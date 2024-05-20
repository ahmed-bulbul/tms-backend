package com.tms.configuaration.payload.request;


import com.tms.common.constant.ErrorId;
import com.tms.common.generics.IDto;
import lombok.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkFLowActionRequestDto implements IDto {
    @NotBlank
    private String name;
    @NotNull(message = ErrorId.ACTION_FLOW_ORDER_NUMBER_IS_NULL)
    @Min(1)
    private Integer orderNumber;
    private String label;
}
