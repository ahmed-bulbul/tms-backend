package com.tms.projects.controller;

import com.tms.common.generics.AbstractSearchController;
import com.tms.common.generics.ISearchService;
import com.tms.projects.dto.ProjectRequestDto;
import com.tms.projects.dto.ProjectSearchDto;
import com.tms.projects.entity.Project;
import com.tms.projects.service.ProjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/project")
public class ProjectController extends AbstractSearchController<Project, ProjectRequestDto, ProjectSearchDto> {

    private final ProjectService projectService;
    public ProjectController(ISearchService<Project, ProjectRequestDto, ProjectSearchDto> iSearchService,ProjectService projectService) {
        super(iSearchService);
        this.projectService = projectService;
    }



}
