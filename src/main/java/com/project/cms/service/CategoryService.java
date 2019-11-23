package com.project.cms.service;

import com.project.cms.entity.Category;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<Category> getAll();

    Category findById(Long catId);

    Long create(Category categoryDetails);

    void update(Long catId, Category categoryDetails);

    void delete(Long catId);


}
