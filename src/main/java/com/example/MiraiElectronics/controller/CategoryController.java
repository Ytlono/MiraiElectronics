package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.repository.Category;
import com.example.MiraiElectronics.repository.CategoryRepository;
import com.example.MiraiElectronics.repository.Product;
import com.example.MiraiElectronics.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

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

    @PostMapping("/{categoryId}/sort")
    public String sorting(@PathVariable Long categoryId, @RequestParam int sortId, Model model) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        model.addAttribute("category", category);
        model.addAttribute("products", productService.sorter(categoryId, sortId));

        return "products";  // Возвращаем отсортированные товары
    }

    @PostMapping("/{categoryId}/filter")
    public String filterByPrice(@PathVariable Long categoryId,
                                @RequestParam(required = false) BigDecimal minPrice,
                                @RequestParam(required = false) BigDecimal maxPrice,
                                Model model){
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        model.addAttribute("category", category);
        List<Product> products = productService.filterByPrice(categoryId,minPrice,maxPrice);
        model.addAttribute("products",products);
        return "products";
    }
}
