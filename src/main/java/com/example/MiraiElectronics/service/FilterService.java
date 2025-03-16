package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FilterService {

    public static Specification<Product> hasPriceBetween(BigDecimal min,BigDecimal max){
        return (root,query,cb) -> cb.between(root.get("price"),min,max);
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
    }

}
