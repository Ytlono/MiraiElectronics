package com.example.MiraiElectronics.service.ProductServices;

import com.example.MiraiElectronics.Mapper.ProductMapper;
import com.example.MiraiElectronics.dto.FilterDTO;
import com.example.MiraiElectronics.dto.ProductDTO;
import com.example.MiraiElectronics.dto.UpdatePrice;
import com.example.MiraiElectronics.dto.UpdateStockQuantity;
import com.example.MiraiElectronics.events.ProductPriceChangedEvent;
import com.example.MiraiElectronics.repository.realization.Product;
import com.example.MiraiElectronics.repository.ProductRepository;
import com.example.MiraiElectronics.service.CategoryService;
import com.example.MiraiElectronics.service.DomainEventPublisher;
import com.example.MiraiElectronics.service.FilterServices.FilterService;
import com.example.MiraiElectronics.service.Generic.GenericEntityService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductService extends GenericEntityService<Product,Long>{
    protected final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;
    private final DomainEventPublisher domainEventPublisher;

    public ProductService(ProductRepository productRepository, CategoryService categoryService, ProductMapper productMapper, DomainEventPublisher domainEventPublisher) {
        super(productRepository);
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
        this.domainEventPublisher = domainEventPublisher;
    }


    public Product addProduct(ProductDTO productDTO){return productRepository.save(toEntity(productDTO));}

    public Product updateProduct(Long id, ProductDTO productDTO) {
        Product product = findById(id);
        productMapper.updateProduct(toEntity(productDTO), product);
        return save(product);
    }

    @Transactional
    public Product updateStockQuantity(UpdateStockQuantity dto) {
        Product product = findById(dto.getProductId());
        product.setStockQuantity(dto.getQuantity());
        return save(product);
    }

    @Transactional
    public BigDecimal changePrice(UpdatePrice dto) {
        Product product = findById(dto.getId());
        BigDecimal oldPrice = product.getPrice();
        product.setPrice(dto.getPrice());
        BigDecimal savedPrice = save(product).getPrice();

        ProductPriceChangedEvent event = createPriceChangedEvent(product, oldPrice, savedPrice);
        domainEventPublisher.publish(event);

        return savedPrice;
    }

    private ProductPriceChangedEvent createPriceChangedEvent(Product product, BigDecimal oldPrice, BigDecimal newPrice) {
        return ProductPriceChangedEvent.builder()
                .productId(product.getProductId())
                .oldPrice(oldPrice)
                .newPrice(newPrice)
                .build();
    }

    public List<Product> findAllByName(String name) {
        return productRepository.findAllByName(name);
    }

    public List<Product> findByCategoryId(Long id) {
        return productRepository.findByCategoryId(id);
    }

    private Sort getSort(int sortId) {
        return switch (sortId) {
            case 1 -> Sort.by(Sort.Direction.ASC, "name");
            case 2 -> Sort.by(Sort.Direction.DESC, "name");
            case 3 -> Sort.by(Sort.Direction.ASC, "price");
            case 4 -> Sort.by(Sort.Direction.DESC, "price");
            default -> Sort.unsorted();
        };
    }

    public List<Product> filterProducts(Long categoryId, FilterDTO filterDTO, int sortId) {
        Specification<Product> spec = Specification.where(FilterService.hasCategory(categoryId));

        if (filterDTO.getMinPrice() != null && filterDTO.getMaxPrice() != null) {
            spec = spec.and(FilterService.hasPriceBetween(filterDTO.getMinPrice(), filterDTO.getMaxPrice()));
        }

        if (filterDTO.getAdditionalFilters() != null && !filterDTO.getAdditionalFilters().isEmpty()) {
            for (Map.Entry<String, Object> entry : filterDTO.getAdditionalFilters().entrySet()) {
                spec = spec.and(FilterService.hasAdditionalSpec(entry.getKey(), entry.getValue()));
            }
        }

        Sort sort = getSort(sortId);

        return productRepository.findAll(spec, sort);
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
