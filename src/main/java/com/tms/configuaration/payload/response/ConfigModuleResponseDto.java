package com.tms.configuaration.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigModuleResponseDto {
    private Long id;
    private String moduleName;
    private String image;
    private Integer order;
    private Boolean isActive;
}
