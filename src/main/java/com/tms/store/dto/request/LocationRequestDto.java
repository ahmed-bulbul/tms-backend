package com.tms.store.dto.request;

import com.tms.common.generics.IDto;
import lombok.Data;

@Data
public class LocationRequestDto  implements IDto {
    private String name;
}
