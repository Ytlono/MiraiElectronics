package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.OrderRequest;
import com.example.MiraiElectronics.repository.realization.Order;
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
public class OrderController extends BaseController{
    private final OrderService orderService;

    public OrderController(SessionService sessionService, OrderService orderService) {
        super(sessionService);
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> getUserOrders(HttpServletRequest request){
        List<Order> orders = orderService.getUserOrders(sessionService.getFullUserFromSession(request));
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/get")
    public ResponseEntity<?> getOrderById(@RequestParam Long orderId, HttpServletRequest request) {
        Order order = orderService.getOrderByIdAndUserId(orderId, getFullUserOrThrow(request));
        if (order == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(order);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        ResponseEntity<?> order = orderService.makeOrder(orderRequest, getFullUserOrThrow(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId, HttpServletRequest request) {
        orderService.cancelOrder(orderId,  getFullUserOrThrow(request));
        return ResponseEntity.ok(Map.of("message", "Заказ отменен"));
    }

}
