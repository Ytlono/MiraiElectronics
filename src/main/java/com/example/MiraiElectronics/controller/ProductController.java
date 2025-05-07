package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.FilterDTO;
import com.example.MiraiElectronics.dto.ProductDTO;
import com.example.MiraiElectronics.repository.realization.Product;
import com.example.MiraiElectronics.service.ProductServices.ProductService;
import com.example.MiraiElectronics.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.addProduct(productDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@RequestParam("id") Long productId){
        productService.deleteProduct(productId);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping
    public ResponseEntity<?> getProduct(@RequestParam("id") Long productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestParam("id") Long productId,ProductDTO productDTO){
        return ResponseEntity.ok(productService.updateProduct(productId,productDTO));
    }

    @GetMapping("/category")
    public ResponseEntity<?> getProductsByCategory(@RequestParam("id") Long categoryId) {
        return ResponseEntity.ok(productService.findByCategoryId(categoryId));
    }

    @PostMapping("/category/sort")
    public ResponseEntity<?> sortProducts(@RequestParam("id") Long categoryId,
                                          @RequestParam("sort") int sortId) {
        return ResponseEntity.ok(productService.sorter(categoryId, sortId));
    }

    @PostMapping("/category/filter")
    public ResponseEntity<?> filterProducts(@RequestParam("id") Long categoryId,
                                            @RequestBody FilterDTO filterDTO) {
        return ResponseEntity.ok(productService.filterProducts(categoryId, filterDTO));
    }
}

