package com.example.MiraiElectronics.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ComputerFilterDTO {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<Integer> ssdList;
    private List<String> brands;
    private Integer minMemory;
    private Integer maxMemory;
}
