package com.tms.projects.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryResponseDto {
    private Long id;
    private String name;
    private String description;
}
