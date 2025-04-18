package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.dto.OrderRequest;
import com.example.MiraiElectronics.repository.OrderRepository;
import com.example.MiraiElectronics.repository.realization.Order;
import com.example.MiraiElectronics.repository.realization.OrderItem;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    public void makeOrder(OrderRequest orderRequest, HttpServletRequest request){
        var user = sessionService.getUserFromSession(request);
        if(user != null)
            return;

        Order order = Order.builder()
                .customerId(user.getId())
                .status(orderRequest.getStatus())
                .shippingAddress(orderRequest.getShippingAddress())
                .orderDate(LocalDate.now())
                .build();
        if(!isOrderSuccessful())
            return;
        orderRepository.save(order);
    }

    public BigDecimal countOrderPrice(OrderRequest orderRequest) {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderItem item : orderRequest.getItems()) {
            if (item.getTotalPrice() != null) {
                totalPrice = totalPrice.add(item.getTotalPrice());
            }
        }

        return totalPrice;
    }

    public boolean isOrderSuccessful(){
        return true;
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
