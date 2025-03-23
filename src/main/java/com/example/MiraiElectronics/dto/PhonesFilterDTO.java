package com.example.MiraiElectronics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhonesFilterDTO implements IFilterDTO{
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<Integer> ssdList;
    private List<Integer> brands;
}
