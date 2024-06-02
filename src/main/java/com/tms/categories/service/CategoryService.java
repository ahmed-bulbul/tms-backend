package com.tms.categories.service;

import com.tms.categories.dto.CategoryRequestDto;
import com.tms.categories.dto.CategorySearchDto;
import com.tms.categories.entity.Category;
import com.tms.common.generics.AbstractRepository;
import com.tms.common.generics.AbstractSearchService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class CategoryService extends AbstractSearchService<Category, CategoryRequestDto, CategorySearchDto> {
    public CategoryService(AbstractRepository<Category> repository) {
        super(repository);
    }

    @Override
    protected Specification<Category> buildSpecification(CategorySearchDto searchDto) {
        return null;
    }

    @Override
    protected <T> T convertToResponseDto(Category category) {
        return null;
    }

    @Override
    protected Category convertToEntity(CategoryRequestDto categoryRequestDto) {
        return null;
    }

    @Override
    protected Category updateEntity(CategoryRequestDto dto, Category entity) {
        return null;
    }
}
