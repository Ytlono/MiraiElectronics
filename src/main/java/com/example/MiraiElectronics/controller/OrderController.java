package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.OrderRequest;
import com.example.MiraiElectronics.dto.OrderStateUpdateDTO;
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
        return ResponseEntity.ok(orderService.getUserOrders(
                sessionService.getFullUserFromSession(request))
        );
    }
    
    @GetMapping("/getOrder")
    public ResponseEntity<?> getOrderById(@RequestParam Long orderId, HttpServletRequest request) {
        return ResponseEntity.ok(
                orderService.getOrderByIdAndUserId(
                        orderId, getFullUserOrThrow(request))
        );
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        ResponseEntity<?> order = orderService.makeOrder(orderRequest, getFullUserOrThrow(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    
    @DeleteMapping
    public ResponseEntity<?> cancelOrder(@RequestParam Long orderId, HttpServletRequest request) {
        orderService.cancelOrder(orderId,  getFullUserOrThrow(request));
        return ResponseEntity.ok(Map.of("message", "Заказ отменен"));
    }

    @PutMapping("/update-status")
    public ResponseEntity<?> updateOrderState(@RequestBody OrderStateUpdateDTO orderStateUpdateDTO){
        return ResponseEntity.ok(orderService.updateOrderStatus(orderStateUpdateDTO.getOrderId(),orderStateUpdateDTO.getStatus()));
    }

}
