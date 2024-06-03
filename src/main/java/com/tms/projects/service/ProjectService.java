package com.tms.projects.service;

import com.tms.categories.service.CategoryService;
import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import com.tms.common.generics.AbstractRepository;
import com.tms.common.generics.AbstractSearchService;
import com.tms.common.models.User;
import com.tms.common.payload.response.UserResponseDto;
import com.tms.common.service.OrganizationService;
import com.tms.common.service.UserService;
import com.tms.common.util.Helper;
import com.tms.common.util.UserUtil;
import com.tms.projects.dto.ProjectRequestDto;
import com.tms.projects.dto.ProjectResponseDto;
import com.tms.projects.dto.ProjectSearchDto;
import com.tms.projects.entity.Project;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
public class ProjectService  extends AbstractSearchService<Project, ProjectRequestDto, ProjectSearchDto> {

    private final OrganizationService organizationService;
    private final CategoryService categoryService;

    private final UserService userService;
    public ProjectService(AbstractRepository<Project> repository, OrganizationService organizationService, CategoryService categoryService, UserService userService) {
        super(repository);
        this.organizationService = organizationService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @Override
    protected Specification<Project> buildSpecification(ProjectSearchDto searchDto) {
        return null;
    }

    @Override
    protected ProjectResponseDto convertToResponseDto(Project project) {
        checkValidDataOfOrganization(project);
        return ProjectResponseDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .category(project.getCategory().getName())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .manager(buildManager(project))
                .teamMembers(buildTeamMembers(project))
                .organizationId(project.getOrganization().getId())
                .build();
    }

    @Override
    protected Project convertToEntity(ProjectRequestDto projectRequestDto) {
        checkMembersOrganization(projectRequestDto);
        return Project.builder()
                .name(projectRequestDto.getName())
                .description(projectRequestDto.getDescription())
                .category(categoryService.findById(projectRequestDto.getCategoryId()))
                .startDate(projectRequestDto.getStartDate())
                .endDate(projectRequestDto.getEndDate())
                .projectManager(User.builder().id(projectRequestDto.getManagerId()).build())
                .teamMembers(projectRequestDto.getTeamMembers().stream()
                        .map(userId -> User.builder().id(userId).build())
                        .collect(Collectors.toSet()))
                .organization(organizationService.findById(prepareOrganizationId(UserUtil.getCurrentUsername())))
                .build();
    }



    @Override
    protected Project updateEntity(ProjectRequestDto dto, Project entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setCategory(categoryService.findById(dto.getCategoryId()));
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setProjectManager(User.builder().id(dto.getManagerId()).build());
        entity.setTeamMembers(dto.getTeamMembers().stream()
                .map(userId -> User.builder().id(userId).build())
                .collect(Collectors.toSet()));
        return entity;
    }

    public static UserResponseDto buildManager(Project project) {
        return UserResponseDto.builder()
                .id(project.getProjectManager().getId())
                .username(project.getProjectManager().getUsername())
                .email(project.getProjectManager().getEmail())
                .build();
    }

    private Set<UserResponseDto> buildTeamMembers(Project project) {
        return project.getTeamMembers().stream()
                .map(user -> UserResponseDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build())
                .collect(Collectors.toSet());
    }


    // This method is used to check if the team members are part of the organization
    private void checkMembersOrganization(ProjectRequestDto projectRequestDto) {
        Long organizationId = prepareOrganizationId(UserUtil.getCurrentUsername());
        projectRequestDto.getTeamMembers().forEach(userId -> {
            Optional<User> user = userService.findById(userId);
            if (user.isEmpty() || !user.get().getOrganization().getId().equals(organizationId)) {
                throw AppServerException.badRequest(Helper.createDynamicCode(ErrorId.INVALID_REQUEST, "team members"));
            }
        });
    }

    // checking data are same organization
    private void checkValidDataOfOrganization(Project project) {
        Long organizationId = prepareOrganizationId(UserUtil.getCurrentUsername());
        if (!project.getOrganization().getId().equals(organizationId)) {
            Logger.getLogger("ProjectService").warning("Invalid request for organization");
        }
    }

    // prepare current user organization id
    private Long prepareOrganizationId(String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(value -> value.getOrganization().getId()).orElseThrow(
                () -> AppServerException.notFound(Helper.createDynamicCode(ErrorId.DATA_NOT_FOUND,username)));
    }
}
