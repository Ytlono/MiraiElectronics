package com.example.MiraiElectronics.events;

import com.example.MiraiElectronics.repository.realization.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
@AllArgsConstructor
public class LowStockEvent {
    private Long productId;
    private String productName;
    private int currentStock;
}

