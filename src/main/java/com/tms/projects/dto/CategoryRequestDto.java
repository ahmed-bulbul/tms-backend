package com.tms.projects.dto;

import com.tms.common.generics.IDto;
import lombok.Data;

@Data
public class CategoryRequestDto implements IDto {
    private String name;
    private String description;
}
