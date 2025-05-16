package com.example.MiraiElectronics.events;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPriceChangedEvent {
    private Long productId;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
}

