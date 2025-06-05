package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.FilterDTO;
import com.example.MiraiElectronics.dto.ProductDTO;
import com.example.MiraiElectronics.dto.UpdatePrice;
import com.example.MiraiElectronics.repository.realization.Product;
import com.example.MiraiElectronics.service.ProductService;
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
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.addProduct(productDTO));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping
    public ResponseEntity<String> deleteProduct(@RequestParam("id") Long productId) {
        productService.deleteById(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping
    public ResponseEntity<Product> getProduct(@RequestParam("id") Long productId) {
        return ResponseEntity.ok(productService.findById(productId));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestParam("id") Long productId,
                                                 @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(productId, productDTO));
    }

    @GetMapping("/category")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam("id") Long categoryId) {
        return ResponseEntity.ok(productService.findByCategoryId(categoryId));
    }

    @PostMapping("/category/filter")
    public ResponseEntity<List<Product>> filterProducts(@RequestParam("id") Long categoryId,
                                                        @RequestParam(name = "sort", defaultValue = "0") int sortId,
                                                        @RequestBody FilterDTO filterDTO) {
        return ResponseEntity.ok(productService.filterProducts(categoryId, filterDTO, sortId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PutMapping("/change-price")
    public ResponseEntity<?> updateProductPrice(@RequestBody UpdatePrice updatePrice){
        return ResponseEntity.ok(productService.changePrice(updatePrice));
    }

}


