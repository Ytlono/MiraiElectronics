package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.realization.Category;
import com.example.MiraiElectronics.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id))
            throw new RuntimeException("Category with id " + id + " not found");
        categoryRepository.deleteById(id);
    }


    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Transactional
    public Category updateCategory(Long id, Category updateCategory) {
        Category category = findById(id);
        category.setName(updateCategory.getName());
        category.setDescription(updateCategory.getDescription());
        return categoryRepository.save(category);
    }



} 