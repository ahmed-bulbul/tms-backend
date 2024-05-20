package com.tms.common.payload.search;

import com.tms.common.payload.dto.SDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class IdQuerySearchDto implements SDto {
    private String query;
    private Long id;
    private Boolean isActive = true;
}
