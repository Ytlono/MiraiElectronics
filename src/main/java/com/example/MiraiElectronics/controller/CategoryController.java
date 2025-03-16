package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.ComputerDTO;
import com.example.MiraiElectronics.dto.IFilterDTO;
import com.example.MiraiElectronics.dto.PhonesFilterDTO;
import com.example.MiraiElectronics.repository.Category;
import com.example.MiraiElectronics.repository.CategoryRepository;
import com.example.MiraiElectronics.repository.Product;
import com.example.MiraiElectronics.service.PhonesService;
import com.example.MiraiElectronics.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final ProductService productService;
    private final PhonesService phonesService;
    private final CategoryRepository categoryRepository;

    public CategoryController(ProductService productService, PhonesService phonesService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.phonesService = phonesService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/{id}")
    public String categorySelector(@PathVariable Long id, Model model) {
        Category category = categoryRepository.findById(id).orElseThrow();
        model.addAttribute("category", category);
        model.addAttribute("products", productService.findByCategoryId(id));

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
    public String filterProducts(@PathVariable Long categoryId,
                                 @RequestParam String type,
                                 @ModelAttribute IFilterDTO filterDTO,
                                 Model model) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        model.addAttribute("category", category);

        List<Product> filteredProducts;

        switch (type) {
            case "phones":
                PhonesFilterDTO phonesFilterDTO = (PhonesFilterDTO) filterDTO;
                filteredProducts = phonesService.filterProducts(categoryId, phonesFilterDTO);
                break;
            case "computers":
                ComputerDTO computerDTO = (ComputerDTO) filterDTO;
                filteredProducts = phonesService.filterProducts(categoryId, computerDTO);
                break;
            default:
                filteredProducts = productService.findByCategoryId(categoryId);
                break;
        }

        model.addAttribute("products", filteredProducts);
        return "products";
    }

}

