package com.example.MiraiElectronics.service.ProductServices;

import com.example.MiraiElectronics.dto.IFilterDTO;
import com.example.MiraiElectronics.dto.PhonesFilterDTO;
import com.example.MiraiElectronics.repository.realization.Product;
import com.example.MiraiElectronics.repository.ProductRepository;
import com.example.MiraiElectronics.service.FilterServices.FilterService;
import com.example.MiraiElectronics.service.FilterServices.PhoneFilterService;
import com.example.MiraiElectronics.service.TemplateSortService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

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
                    .and(PhoneFilterService.hasSSD(SSD));

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
                    .and(PhoneFilterService.hasRAM(RAM));

            List<Product> filteredProducts = productRepository.findAll(spec);
            products.addAll(filteredProducts);
        }
        return products;
    }


    public  List<Product> filterByColor(Long categoryId, List<String> colorList){
        List<Product> products = new ArrayList<>();

        for (String color : colorList) {
            Specification<Product> spec = Specification
                    .where(FilterService.hasCategory(categoryId))
                    .and(PhoneFilterService.hasColor(color));
            List<Product> filteredProducts = productRepository.findAll(spec);
            products.addAll(filteredProducts);
        }
         return products;
    }

    public List<Product> filterByAdditionalSpec(Long categoryId, String nameSpec, Object value) {
        Set<Product> productSet = new HashSet<>();

        if (value instanceof String valStr) {
            Specification<Product> spec = Specification
                    .where(FilterService.hasCategory(categoryId))
                    .and(PhoneFilterService.hasAdditionalSpec(nameSpec, valStr));
            productSet.addAll(productRepository.findAll(spec));
        } else if (value instanceof List<?> valList) {
            for (Object val : valList) {
                if (val instanceof String valStr) {
                    Specification<Product> spec = Specification
                            .where(FilterService.hasCategory(categoryId))
                            .and(PhoneFilterService.hasAdditionalSpec(nameSpec, valStr));
                    productSet.addAll(productRepository.findAll(spec));
                }
            }
        }

        return new ArrayList<>(productSet);
    }


    @Override
    public List<Product> filterProducts(Long categoryId, IFilterDTO filterDTO) {
        PhonesFilterDTO phonesFilterDTO = (PhonesFilterDTO) filterDTO;

        Specification<Product> spec = Specification.where(FilterService.hasCategory(categoryId));

        if (phonesFilterDTO.getMinPrice() != null && phonesFilterDTO.getMaxPrice() != null) {
            spec = spec.and(FilterService.hasPriceBetween(
                    phonesFilterDTO.getMinPrice(), phonesFilterDTO.getMaxPrice()));
        }

        List<Product> filteredProducts = productRepository.findAll(spec);

//        if (phonesFilterDTO.getSsdList() != null && !phonesFilterDTO.getSsdList().isEmpty()) {
//            List<Product> bySSD = filterBySSD(categoryId, phonesFilterDTO.getSsdList());
//            filteredProducts.retainAll(bySSD);
//        }
//
//        if (phonesFilterDTO.getRamList() != null && !phonesFilterDTO.getRamList().isEmpty()) {
//            List<Product> byRAM = filterByRAM(categoryId, phonesFilterDTO.getRamList());
//            filteredProducts.retainAll(byRAM);
//        }
//
//        if (phonesFilterDTO.getColorList() != null && !phonesFilterDTO.getColorList().isEmpty()) {
//            List<Product> byColor = filterByColor(categoryId, phonesFilterDTO.getColorList());
//            filteredProducts.retainAll(byColor);
//        }

        if(phonesFilterDTO.getAdditionalFilters() != null && !phonesFilterDTO.getAdditionalFilters().isEmpty()){
            for (Map.Entry<String,Object> entry : phonesFilterDTO.getAdditionalFilters().entrySet()){
                filteredProducts.retainAll(filterByAdditionalSpec(categoryId, entry.getKey(),entry.getValue()));
            }
        }
        return filteredProducts;
    }
}
