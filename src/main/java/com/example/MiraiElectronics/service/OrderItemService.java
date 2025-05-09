package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.OrderItemRepository;
import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.repository.realization.Order;
import com.example.MiraiElectronics.repository.realization.OrderItem;
import com.example.MiraiElectronics.service.Generic.GenericEntityService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService extends GenericEntityService<OrderItem,Long> {
    private final OrderItemRepository orderItemRepository;
    private final CartItemService cartItemService;

    public OrderItemService(OrderItemRepository orderItemRepository, CartItemService cartItemService) {
        super(orderItemRepository);
        this.orderItemRepository = orderItemRepository;
        this.cartItemService = cartItemService;
    }

    public List<OrderItem> convertCartItemsToOrderItems(List<Long> ids, Order order) {
        List<CartItem> cartItems = cartItemService.getAllById(ids);
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = OrderItem.builder()
                    .product(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .unitPrice(cartItem.getProduct().getPrice())
                    .totalPrice(cartItem.getPrice())
                    .order(order)
                    .build();
            orderItems.add(orderItem);
        }
        return orderItems;
    }
}
