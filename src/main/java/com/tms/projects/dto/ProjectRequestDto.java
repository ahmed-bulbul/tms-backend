package com.tms.projects.dto;

import com.tms.common.generics.IDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;


@Data
public class ProjectRequestDto implements IDto {

    private String name;
    private String description;
    private Long categoryId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long managerId;
    private Set<Long> teamMembers;


}
