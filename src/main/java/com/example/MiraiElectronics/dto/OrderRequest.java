package com.example.MiraiElectronics.dto;

import com.example.MiraiElectronics.repository.realization.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String status;
    private String shippingAddress;

    private List<OrderItem> items;
}
