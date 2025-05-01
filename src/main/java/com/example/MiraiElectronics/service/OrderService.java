package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.dto.OrderRequest;
import com.example.MiraiElectronics.repository.OrderRepository;
import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.repository.realization.Order;
import com.example.MiraiElectronics.repository.realization.OrderItem;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final SessionService sessionService;
    private final PaymentService paymentService;

    public OrderService(OrderRepository orderRepository, OrderItemService orderItemService, SessionService sessionService, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.sessionService = sessionService;
        this.paymentService = paymentService;
    }

    public ResponseEntity<?> makeOrder(OrderRequest orderRequest, HttpServletRequest request) {
        var user = sessionService.getUserFromSession(request);
        if (user == null)
            return ResponseEntity.ok("User not found");

        String shippingAddress = user.getAddress()!= null ? user.getAddress() : orderRequest.getShippingAddress();

        Order order = Order.builder()
                .customerId(user.getId())
                .status(orderRequest.getStatus())
                .shippingAddress(shippingAddress)
                .orderDate(LocalDate.now())
                .build();

        List<OrderItem> orderItems = orderItemService.convertCartItemsToOrderItems(orderRequest.getItemsId(), order);
        order.setOrderItems(orderItems);

        order.setTotalAmount(
                orderItems.stream()
                        .map(OrderItem::getTotalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        if (!isOrderSuccessful())
            return ResponseEntity.ok("Payment failed");

        return ResponseEntity.ok(orderRepository.save(order));
    }


    public boolean isOrderSuccessful(){
        return paymentService.isPayed();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }

    public void deleteOrderById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    public void updateShippingAddress(Long orderId, String newAddress) {
        Order order = getOrderById(orderId);
        order.setShippingAddress(newAddress);
        orderRepository.save(order);
    }

    public void updateOrderStatus(Long id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        orderRepository.save(order);
    }
}
