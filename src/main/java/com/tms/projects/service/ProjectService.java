package com.tms.projects.service;

import com.tms.common.generics.AbstractRepository;
import com.tms.common.generics.AbstractSearchService;
import com.tms.common.models.User;
import com.tms.common.payload.response.UserResponseDto;
import com.tms.projects.dto.ProjectRequestDto;
import com.tms.projects.dto.ProjectResponseDto;
import com.tms.projects.dto.ProjectSearchDto;
import com.tms.projects.entity.CategoryEnum;
import com.tms.projects.entity.Project;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ProjectService  extends AbstractSearchService<Project, ProjectRequestDto, ProjectSearchDto> {
    public ProjectService(AbstractRepository<Project> repository) {
        super(repository);
    }

    @Override
    protected Specification<Project> buildSpecification(ProjectSearchDto searchDto) {
        return null;
    }

    @Override
    protected ProjectResponseDto convertToResponseDto(Project project) {
        return ProjectResponseDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .category(CategoryEnum.byId(project.getCategory().getCategoryTypeId()).name())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .manager(buildManager(project))
                .teamMembers(buildTeamMembers(project))
                .build();
    }

    @Override
    protected Project convertToEntity(ProjectRequestDto projectRequestDto) {
        return Project.builder()
                .name(projectRequestDto.getName())
                .description(projectRequestDto.getDescription())
                .category(CategoryEnum.byId(projectRequestDto.getCategory().getCategoryTypeId()))
                .startDate(projectRequestDto.getStartDate())
                .endDate(projectRequestDto.getEndDate())
                .projectManager(User.builder().id(projectRequestDto.getManagerId()).build())
                .teamMembers(projectRequestDto.getTeamMembers().stream()
                        .map(userId -> User.builder().id(userId).build())
                        .collect(Collectors.toSet()))
                .build();
    }

    @Override
    protected Project updateEntity(ProjectRequestDto dto, Project entity) {
        return null;
    }

    public static UserResponseDto buildManager(Project project) {
        return UserResponseDto.builder()
                .id(project.getProjectManager().getId())
                .username(project.getProjectManager().getUsername())
                .build();
    }

    private Set<UserResponseDto> buildTeamMembers(Project project) {
        return project.getTeamMembers().stream()
                .map(user -> UserResponseDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .build())
                .collect(Collectors.toSet());
    }
}
