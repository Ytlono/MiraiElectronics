package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.ComputerDTO;
import com.example.MiraiElectronics.dto.PhonesFilterDTO;
import com.example.MiraiElectronics.repository.realization.Category;
import com.example.MiraiElectronics.repository.CategoryRepository;
import com.example.MiraiElectronics.repository.realization.Product;
import com.example.MiraiElectronics.service.ProductServices.PhonesService;
import com.example.MiraiElectronics.service.ProductServices.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
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
    public ResponseEntity<?> getCategoryProducts(@PathVariable Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        List<Product> products = productService.findByCategoryId(id);

        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/{categoryId}/sort")
    public ResponseEntity<?> sortProducts(@PathVariable Long categoryId, @RequestParam int sortId) {
        categoryRepository.findById(categoryId).orElseThrow();
        List<Product> sortedProducts = productService.sorter(categoryId, sortId);

        return ResponseEntity.ok().body(sortedProducts);
    }

    @PostMapping("/{categoryId}/filter/phones")
    public ResponseEntity<?> filterPhones(@PathVariable Long categoryId,
                                          @RequestBody PhonesFilterDTO filterDTO) {
        List<Product> filtered = phonesService.filterProducts(categoryId, filterDTO);
        return ResponseEntity.ok(filtered);
    }

    @PostMapping("/{categoryId}/filter/computers")
    public ResponseEntity<?> filterComputers(@PathVariable Long categoryId,
                                             @RequestBody ComputerDTO filterDTO) {
        List<Product> filtered = phonesService.filterProducts(categoryId, filterDTO);
        return ResponseEntity.ok(filtered);
    }

}


