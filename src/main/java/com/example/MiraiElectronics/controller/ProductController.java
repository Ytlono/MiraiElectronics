package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.repository.realization.Product;
import com.example.MiraiElectronics.service.ProductServices.ProductService;
import com.example.MiraiElectronics.service.CategoryService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category/{categoryId}")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/product/{productId}")
    public String getProduct(@PathVariable Long categoryId, @PathVariable Long productId, Model model) {
        Product product = productService.findById(productId);
        
        model.addAttribute("product", product);
        model.addAttribute("category", categoryService.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found")));
        
        return "product";
    }
}
