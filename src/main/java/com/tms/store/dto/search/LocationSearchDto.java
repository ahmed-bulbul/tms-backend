package com.tms.store.dto.search;

import com.tms.common.generics.SDto;
import lombok.Data;

@Data
public class LocationSearchDto implements SDto {
    private String name;
}
