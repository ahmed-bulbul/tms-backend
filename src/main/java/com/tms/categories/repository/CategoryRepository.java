package com.tms.categories.repository;


import com.tms.categories.entity.Category;
import com.tms.common.generics.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends AbstractRepository<Category> {
}
