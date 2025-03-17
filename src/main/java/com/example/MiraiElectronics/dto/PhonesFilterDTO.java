package com.example.MiraiElectronics.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PhonesFilterDTO implements IFilterDTO{
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<Integer> ssdList;
    private List<Integer> brands;
}
