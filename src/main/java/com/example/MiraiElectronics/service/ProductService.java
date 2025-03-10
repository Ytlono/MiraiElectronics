package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.Product;
import com.example.MiraiElectronics.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final TemplateSortService templateSortService;

    public ProductService(ProductRepository productRepository, TemplateSortService templateSortService) {
        this.productRepository = productRepository;
        this.templateSortService = templateSortService;
    }


    public List<Product> findAllByName(String name){
        return productRepository.findAllByName(name);
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public List<Product> findByCategoryId(Long id){
        return productRepository.findByCategoryId(id);
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow();
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public List<Product> sortByPriceUp() {
        List<Product> products = findAll();
        return templateSortService.sort(products, compareByPrice());
    }

    public List<Product> sortByPriceDown() {
        List<Product> products = findAll();
        return templateSortService.sort(products, compareByPriceDescending());
    }

    private Comparator<Product> compareByPriceDescending() {
        return (p1, p2) -> p2.getPrice().compareTo(p1.getPrice());
    }

    public List<Product> sortByNameAsc() {
        List<Product> products = findAll();
        return templateSortService.sort(products, compareByNameAscending());
    }

    public List<Product> sortByNameDesc() {
        List<Product> products = findAll();
        return templateSortService.sort(products, compareByNameDescending());
    }

    private Comparator<Product> compareByPrice() {
        return (p1, p2) -> p1.getPrice().compareTo(p2.getPrice());
    }

    private Comparator<Product> compareByNameAscending() {
        return (p1, p2) -> p1.getName().compareToIgnoreCase(p2.getName());
    }

    private Comparator<Product> compareByNameDescending() {
        return (p1, p2) -> p2.getName().compareToIgnoreCase(p1.getName());
    }
}
