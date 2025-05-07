package com.example.MiraiElectronics.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @Positive(message = "Category ID must be positive")
    private Long categoryId;

    @Positive(message = "Brand ID must be positive")
    private Long brandId;

    @NotBlank(message = "Model must not be blank")
    private String model;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @Min(value = 0, message = "Stock quantity must be 0 or more")
    private int stockQuantity;

    private String description;

    private Map<String, Object> attributes;
}