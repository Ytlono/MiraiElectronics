package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.OrderRequest;
import com.example.MiraiElectronics.repository.realization.Order;
import com.example.MiraiElectronics.service.OrderItemService;
import com.example.MiraiElectronics.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;


    public OrderController(OrderService orderService, OrderItemService orderItemService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @PostMapping("/make_order")
    public ResponseEntity<?> makeOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        Order order = orderService.makeOrder(orderRequest, request);
        return ResponseEntity.ok(order);
    }

}
