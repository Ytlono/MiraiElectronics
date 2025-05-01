package com.example.MiraiElectronics.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class PhonesFilterDTO {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    private Map<String, Object> additionalFilters = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalFilter(String key, Object value) {
        this.additionalFilters.put(key, value);
    }
}
