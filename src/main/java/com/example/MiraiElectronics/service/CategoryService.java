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

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return categoryRepository.existsById(id);
    }

    @Transactional
    public void updateCategory(Long id, Category updateCategory) {
        Category category = findById(id).orElseThrow();
        category.setName(updateCategory.getName());
        category.setDescription(updateCategory.getDescription());
        categoryRepository.save(category);
    }



} 