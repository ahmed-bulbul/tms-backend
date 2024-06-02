package com.tms.categories.controller;


import com.tms.categories.dto.CategoryRequestDto;
import com.tms.categories.dto.CategorySearchDto;
import com.tms.categories.entity.Category;
import com.tms.common.generics.AbstractSearchController;
import com.tms.common.generics.ISearchService;
import com.tms.projects.dto.ProjectRequestDto;
import com.tms.projects.dto.ProjectSearchDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController extends AbstractSearchController<Category, CategoryRequestDto, CategorySearchDto> {

    public CategoryController(ISearchService<Category, CategoryRequestDto, CategorySearchDto> iSearchService) {
        super(iSearchService);
    }
}
