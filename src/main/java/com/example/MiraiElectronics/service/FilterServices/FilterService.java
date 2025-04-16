package com.example.MiraiElectronics.service.FilterServices;

import com.example.MiraiElectronics.repository.realization.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FilterService {

    public static Specification<Product> hasPriceBetween(BigDecimal min,BigDecimal max){
        return (root,query,cb) -> cb.between(root.get("price"),min,max);
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> hasAdditionalSpec(String name, Object value){
        return (root, query, cb) -> cb.equal(
                cb.function("jsonb_extract_path_text", String.class,
                        root.get("attributes"),
                        cb.literal(name)),
                String.valueOf(value)
        );
    }
}
