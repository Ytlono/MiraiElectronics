package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.OrderRequest;
import com.example.MiraiElectronics.dto.UserSessionDTO;
import com.example.MiraiElectronics.repository.realization.Order;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.OrderItemService;
import com.example.MiraiElectronics.service.OrderService;
import com.example.MiraiElectronics.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final SessionService sessionService;

    public OrderController(OrderService orderService, OrderItemService orderItemService, SessionService sessionService) {
        this.orderService = orderService;
        this.orderItemService = orderItemService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<?> getUserOrders(HttpServletRequest request) {
        UserSessionDTO user = sessionService.getUserFromSession(request);

        List<Order> orders = orderService.getUserOrders(user.getId());
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId, HttpServletRequest request) {
        User user = sessionService.getFullUserFromSession(request);

        Order order = orderService.getOrderByIdAndUserId(orderId, user);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        UserSessionDTO user = sessionService.getUserFromSession(request);
        ResponseEntity<?> order = orderService.makeOrder(orderRequest, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId, HttpServletRequest request) {
        orderService.cancelOrder(orderId, sessionService.getFullUserFromSession(request));
        return ResponseEntity.ok(Map.of("message", "Заказ отменен"));
    }
}
