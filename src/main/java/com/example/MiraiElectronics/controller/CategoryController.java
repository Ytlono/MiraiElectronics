package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.ComputerFilterDTO;
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

        IFilterDTO filterDTO = null;
        if (category.getCategoryId() == 1) {
            filterDTO = new PhonesFilterDTO();  // Или создайте другой фильтр для компьютеров
        } else if (category.getCategoryId() == 2) {
            filterDTO = new ComputerFilterDTO();
        }
        model.addAttribute("category", category);
        model.addAttribute("products", productService.findByCategoryId(id));
        model.addAttribute("filterDTO", filterDTO);
        return "products";
    }


    @PostMapping("/{categoryId}/sort")
    public String sorting(@PathVariable Long categoryId, @RequestParam int sortId, Model model) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        model.addAttribute("category", category);
        model.addAttribute("products", productService.sorter(categoryId, sortId));

        return "products";
    }

    @PostMapping("/{categoryId}/filter")
    public String filterProducts(@PathVariable Long categoryId,
                                 @ModelAttribute PhonesFilterDTO filterDTO,
                                 Model model) {
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        model.addAttribute("category", category);

        List<Product> filteredProducts;

        switch (categoryId.intValue()) {
            case 1: // Телефоны
                PhonesFilterDTO phonesFilterDTO = (PhonesFilterDTO) filterDTO;
                filteredProducts = phonesService.filterProducts(categoryId, phonesFilterDTO);
                break;
            default:
                filteredProducts = productService.findByCategoryId(categoryId);
                break;
        }

        model.addAttribute("products", filteredProducts);
        return "products";
    }


}

