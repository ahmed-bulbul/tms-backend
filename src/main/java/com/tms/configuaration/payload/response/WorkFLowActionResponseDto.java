package com.tms.configuaration.payload.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkFLowActionResponseDto {
    private Long id;
    private String name;
    private Integer orderNumber;
    private boolean editable;
    private boolean actionable;
    private String label;
}
