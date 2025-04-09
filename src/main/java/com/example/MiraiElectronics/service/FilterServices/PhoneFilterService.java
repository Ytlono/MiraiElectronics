package com.example.MiraiElectronics.service.FilterServices;

import com.example.MiraiElectronics.repository.realization.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PhoneFilterService extends FilterService {

    public static Specification<Product> hasSSD(int memory) {
        return (root, query, cb) -> cb.equal(
                cb.function("jsonb_extract_path_text", String.class,
                        root.get("attributes"),
                        cb.literal("SSD")),
                String.valueOf(memory)
        );
    }

    public static Specification<Product> hasRAM(int memory) {
        return (root, query, cb) -> cb.equal(
                cb.function("jsonb_extract_path_text", String.class,
                        root.get("attributes"),
                        cb.literal("RAM")),
                String.valueOf(memory)
        );
    }

    public static   Specification<Product> hasColor(String color){
        return (root, query, cb) -> cb.equal(
                cb.function("jsonb_extract_path_text", String.class,
                        root.get("attributes"),
                        cb.literal("color")),
                String.valueOf(color)
        );
    }

    public static   Specification<Product> hasBattery(int battery){
        return (root, query, cb) -> cb.equal(
                cb.function("jsonb_extract_path_text", String.class,
                        root.get("attributes"),
                        cb.literal("battery")),
                String.valueOf(battery)
        );
    }
}
