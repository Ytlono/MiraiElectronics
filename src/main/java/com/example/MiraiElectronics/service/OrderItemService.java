package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.OrderItemRepository;
import com.example.MiraiElectronics.repository.realization.OrderItem;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public void createOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }
}
