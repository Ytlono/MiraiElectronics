package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.ComputerDTO;
import com.example.MiraiElectronics.dto.PhonesFilterDTO;
import com.example.MiraiElectronics.repository.realization.Product;
import com.example.MiraiElectronics.service.ProductServices.ProductService;
import com.example.MiraiElectronics.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.findByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/category/{categoryId}/sort")
    public ResponseEntity<?> sortProducts(@PathVariable Long categoryId, @RequestParam int sortId) {
        List<Product> sortedProducts = productService.sorter(categoryId, sortId);
        return ResponseEntity.ok(sortedProducts);
    }

    @PostMapping("/category/{categoryId}/filter")
    public ResponseEntity<?> filterProducts(@PathVariable Long categoryId,
                                          @RequestBody PhonesFilterDTO filterDTO) {
        List<Product> filtered = productService.filterProducts(categoryId, filterDTO);
        return ResponseEntity.ok(filtered);
    }
}
