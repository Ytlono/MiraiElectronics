package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.realization.Category;
import com.example.MiraiElectronics.repository.CategoryRepository;
import com.example.MiraiElectronics.service.Generic.GenericEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService extends GenericEntityService<Category,Long> {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category updateCategory(Long id, Category updateCategory) {
        Category category = findById(id);
        category.setName(updateCategory.getName());
        category.setDescription(updateCategory.getDescription());
        return categoryRepository.save(category);
    }
} 