package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.dto.IFilterDTO;
import com.example.MiraiElectronics.dto.PhonesFilterDTO;
import com.example.MiraiElectronics.repository.Product;
import com.example.MiraiElectronics.repository.ProductRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhonesService extends ProductService {

    public PhonesService(ProductRepository productRepository, TemplateSortService templateSortService) {
        super(productRepository, templateSortService);
    }

    public List<Product> filterBySSD(Long categoryId, List<Integer> SSDList) {
        List<Product> products = new ArrayList<>();

        for (int SSD : SSDList) {
            Specification<Product> spec = Specification
                    .where(FilterService.hasCategory(categoryId))
                    .and(FilterService.hasSSD(SSD));

            List<Product> filteredProducts = productRepository.findAll(spec);
            products.addAll(filteredProducts);
        }
        return products;
    }

    public List<Product> filterByRAM(Long categoryId, List<Integer> RAMList) {
        List<Product> products = new ArrayList<>();

        for (int RAM : RAMList) {
            Specification<Product> spec = Specification
                    .where(FilterService.hasCategory(categoryId))
                    .and(FilterService.hasSSD(RAM));

            List<Product> filteredProducts = productRepository.findAll(spec);
            products.addAll(filteredProducts);
        }
        return products;
    }

    @Override
    public List<Product> filterProducts(Long categoryId, IFilterDTO filterDTO) {
        PhonesFilterDTO phonesFilterDTO = (PhonesFilterDTO) filterDTO;

        Specification<Product> spec = Specification.where(FilterService.hasCategory(categoryId));

        if (phonesFilterDTO.getMinPrice() != null && phonesFilterDTO.getMaxPrice() != null) {
            spec = spec.and(FilterService.hasPriceBetween(
                    phonesFilterDTO.getMinPrice(), phonesFilterDTO.getMaxPrice()));
        }

        if (phonesFilterDTO.getSsdList() != null && !phonesFilterDTO.getSsdList().isEmpty()) {
            Specification<Product> ssdSpec = null;
            for (Integer ssd : phonesFilterDTO.getSsdList()) {
                Specification<Product> singleSsdSpec = FilterService.hasSSD(ssd);
                ssdSpec = (ssdSpec == null) ? singleSsdSpec : ssdSpec.or(singleSsdSpec);
            }
            spec = spec.and(ssdSpec);
        }

        return productRepository.findAll(spec);
    }
}
