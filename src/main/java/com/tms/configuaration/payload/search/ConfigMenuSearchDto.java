package com.tms.configuaration.payload.search;

import com.tms.common.payload.search.IdQuerySearchDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ConfigMenuSearchDto extends IdQuerySearchDto {
    private Boolean isWorkflow;
}
