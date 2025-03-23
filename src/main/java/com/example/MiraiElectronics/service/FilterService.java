package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.*;
import java.math.BigDecimal;

@Service
public class FilterService {

    public static Specification<Product> hasPriceBetween(BigDecimal min, BigDecimal max) {
        return (root, query, cb) -> cb.between(root.get("price"), min, max);
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, cb) -> cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> hasSSD(int memory) {
        String attributeValue = String.valueOf(memory);
        return (root, query, cb) -> {
            Expression<String> storage = cb.function(
                    "jsonb_extract_path_text",
                    String.class,
                    root.get("attributes"),
                    cb.literal("storage"));
            return cb.equal(storage, attributeValue);
        };
    }

    public static Specification<Product> hasRAM(int ram) {
        String attributeValue = String.valueOf(ram);
        return (root, query, cb) -> cb.equal(root.get("attributes").get("ram"), attributeValue);
    }

    public static Specification<Product> hasColor(String color) {
        return (root, query, cb) -> cb.equal(root.get("attributes").get("color"), color);
    }

    public static Specification<Product> hasBattery(int battery) {
        String attributeValue = String.valueOf(battery);
        return (root, query, cb) -> cb.equal(root.get("attributes").get("battery"), attributeValue);
    }
}
