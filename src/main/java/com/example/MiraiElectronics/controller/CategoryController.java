package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.repository.Category;
import com.example.MiraiElectronics.repository.CategoryRepository;
import com.example.MiraiElectronics.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    public CategoryController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }


    @GetMapping("/{id}")
    public String categorySelector(@PathVariable Long id, Model model){
        Category category = categoryRepository.findById(id).orElseThrow();
        model.addAttribute("category",category);
        model.addAttribute("products",productService.findByCategoryId(id));

        return "products";
    }
}
