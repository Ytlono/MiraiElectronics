package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.repository.realization.Category;
import com.example.MiraiElectronics.repository.CategoryRepository;
import com.example.MiraiElectronics.service.CategoryService;
import com.example.MiraiElectronics.service.ProductServices.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")

    @GetMapping("/get")
    public ResponseEntity<?> getCategory(@RequestParam Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        return ResponseEntity.ok(
                categoryService.save(category)
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCategory(@RequestParam Long id){
        categoryService.deleteById(id);
        return ResponseEntity.ok("deleted category with id: " + id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestParam Long id,@RequestBody Category updateCategory){
        return ResponseEntity.ok(
                categoryService.updateCategory(id,updateCategory)
        );
    }

}