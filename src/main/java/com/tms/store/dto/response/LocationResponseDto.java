package com.tms.store.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationResponseDto  {
    private Long id;
    private String name;
}
