package com.project.cms.service.impl;

import com.project.cms.entity.Category;
import com.project.cms.pagination.PageModel;
import com.project.cms.repository.CategoryRepository;
import com.project.cms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PageModel pageModel;

    @Override
    public Page<Category> getAll() {
        pageModel.setSIZE(8);
        pageModel.initPageAndSize();
        return categoryRepository.findAll(PageRequest.of(pageModel.getPAGE(), pageModel.getSIZE()));
    }

    @Override
    public Category findById(Long catId) {
        Optional<Category> categoryOptional = categoryRepository.findById(catId);
        if (!categoryOptional.isPresent()) {
            throw new RuntimeException("Category Not Found!");
        }
        return categoryOptional.get();
    }


    @Override
    public Long create(Category categoryDetails) {
       /* categoryDetails.setDatecreated(Calendar.getInstance().getTimeInMillis());*/
        categoryRepository.save(categoryDetails);
        return categoryDetails.getId();
    }

    @Override
    public void update(Long catId, Category categoryDetails) {
        Category currentCat = findById(catId);
        currentCat.setName(categoryDetails.getName());
        categoryRepository.save(currentCat);
    }

    @Override
    public void delete(Long catId) {
        categoryRepository.deleteById(catId);
    }
}
