package com.example.MiraiElectronics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDTO {
    private String fullCardNumber;
    private String cvv;
    private String expiryDate;

    //потом в коммерческом проекте удалю :)
    private BigDecimal balance;
}
