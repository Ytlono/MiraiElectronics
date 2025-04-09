package com.example.MiraiElectronics.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class PhonesFilterDTO implements IFilterDTO {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
//    private List<Integer> ssdList;
//    private List<Integer> ramList;
//    private List<String> colorList;
//    private Integer minMemory;
//    private Integer maxMemory;
    private List<String> brands;

    private Map<String, Object> additionalFilters = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalFilter(String key, Object value) {
        this.additionalFilters.put(key, value);
    }
}
