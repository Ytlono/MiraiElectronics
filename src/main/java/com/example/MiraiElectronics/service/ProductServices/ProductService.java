package com.example.MiraiElectronics.service.ProductServices;

import com.example.MiraiElectronics.Mapper.ProductMapper;
import com.example.MiraiElectronics.dto.FilterDTO;
import com.example.MiraiElectronics.dto.ProductDTO;
import com.example.MiraiElectronics.repository.realization.Product;
import com.example.MiraiElectronics.repository.ProductRepository;
import com.example.MiraiElectronics.service.CategoryService;
import com.example.MiraiElectronics.service.FilterServices.FilterService;
import com.example.MiraiElectronics.service.TemplateSortService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {
    protected final ProductRepository productRepository;
    private final TemplateSortService templateSortService;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, TemplateSortService templateSortService, CategoryService categoryService, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.templateSortService = templateSortService;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }

    public Product addProduct(ProductDTO productDTO){return productRepository.save(toEntity(productDTO));}

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product product = findById(id);
        productMapper.updateProduct(toEntity(productDTO), product);
        return productRepository.save(product);
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


    public List<Product> filterByAdditionalSpec(Long categoryId, String nameSpec, Object value) {
        Set<Product> productSet = new HashSet<>();

        if (value instanceof String valStr) {
            Specification<Product> spec = Specification
                    .where(FilterService.hasCategory(categoryId))
                    .and(FilterService.hasAdditionalSpec(nameSpec, valStr));
            productSet.addAll(productRepository.findAll(spec));
        } else if (value instanceof List<?> valList) {
            for (Object val : valList) {
                if (val instanceof String valStr) {
                    Specification<Product> spec = Specification
                            .where(FilterService.hasCategory(categoryId))
                            .and(FilterService.hasAdditionalSpec(nameSpec, valStr));
                    productSet.addAll(productRepository.findAll(spec));
                }
            }
        }

        return new ArrayList<>(productSet);
    }

    public List<Product> filterProducts(Long categoryId, FilterDTO filterDTO) {
        FilterDTO phonesFilterDTO = (FilterDTO) filterDTO;

        Specification<Product> spec = Specification.where(FilterService.hasCategory(categoryId));

        if (phonesFilterDTO.getMinPrice() != null && phonesFilterDTO.getMaxPrice() != null) {
            spec = spec.and(FilterService.hasPriceBetween(
                    phonesFilterDTO.getMinPrice(), phonesFilterDTO.getMaxPrice()));
        }

        List<Product> filteredProducts = productRepository.findAll(spec);

        if(phonesFilterDTO.getAdditionalFilters() != null && !phonesFilterDTO.getAdditionalFilters().isEmpty()){
            for (Map.Entry<String,Object> entry : phonesFilterDTO.getAdditionalFilters().entrySet()){
                filteredProducts.retainAll(filterByAdditionalSpec(categoryId, entry.getKey(),entry.getValue()));
            }
        }
        return filteredProducts;
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

    public Product toEntity(ProductDTO dto) {
        return Product.builder()
                .name(dto.getName())
                .category(categoryService.findById(dto.getCategoryId()))
                .brandId(dto.getBrandId())
                .model(dto.getModel())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .description(dto.getDescription())
                .attributes(dto.getAttributes())
                .build();
    }
}
