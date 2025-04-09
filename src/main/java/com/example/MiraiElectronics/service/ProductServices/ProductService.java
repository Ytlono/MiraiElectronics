package com.example.MiraiElectronics.service.ProductServices;

import com.example.MiraiElectronics.dto.IFilterDTO;
import com.example.MiraiElectronics.repository.realization.Product;
import com.example.MiraiElectronics.repository.ProductRepository;
import com.example.MiraiElectronics.service.TemplateSortService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {
    protected final ProductRepository productRepository;
    private final TemplateSortService templateSortService;

    public ProductService(ProductRepository productRepository, TemplateSortService templateSortService) {
        this.productRepository = productRepository;
        this.templateSortService = templateSortService;
    }

    public List<Product> findAllByName(String name) {
        return productRepository.findAllByName(name);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCategoryId(Long id) {
        return productRepository.findByCategoryId(id);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> sorter(Long categoryId, int sortId) {
        return switch (sortId) {
            case 1 -> sortByNameAsc(categoryId);
            case 2 -> sortByNameDesc(categoryId);
            case 3 -> sortByPriceUp(categoryId);
            case 4 -> sortByPriceDown(categoryId);
            default -> findByCategoryId(categoryId);
        };
    }

    public List<Product> sortByPriceUp(Long id) {
        List<Product> products = findByCategoryId(id);
        return templateSortService.sort(products, compareByPrice());
    }

    public List<Product> sortByPriceDown(Long id) {
        List<Product> products = findByCategoryId(id);
        return templateSortService.sort(products, compareByPriceDescending());
    }

    public List<Product> sortByNameAsc(Long id) {
        List<Product> products = findByCategoryId(id);
        return templateSortService.sort(products, compareByNameAscending());
    }

    public List<Product> sortByNameDesc(Long id) {
        List<Product> products = findByCategoryId(id);
        return templateSortService.sort(products, compareByNameDescending());
    }


    public List<Product> filterProducts(Long categoryId, IFilterDTO filterDTO) {
        return productRepository.findAll();
    }



    private Comparator<Product> compareByPrice() {
        return Comparator.comparing(Product::getPrice);
    }

    private Comparator<Product> compareByPriceDescending() {
        return Comparator.comparing(Product::getPrice).reversed();
    }

    private Comparator<Product> compareByNameAscending() {
        return Comparator.comparing(p -> p.getName().toLowerCase());
    }

    private Comparator<Product> compareByNameDescending() {
        return Comparator.comparing((Product p) -> p.getName().toLowerCase()).reversed();
    }
}
