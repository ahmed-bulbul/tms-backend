package com.tms.projects.dto;

import com.tms.common.payload.response.UserResponseDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Data
public class ProjectResponseDto {
    private Long id;
    private String name;
    private String description;
    private String category;
    private Long categoryId;
    private LocalDate startDate;
    private LocalDate endDate;
    private UserResponseDto manager;
    private Set<UserResponseDto> teamMembers;
    private Long organizationId;

}
