package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.dto.OrderRequest;
import com.example.MiraiElectronics.dto.UserSessionDTO;
import com.example.MiraiElectronics.repository.OrderRepository;
import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.repository.realization.Order;
import com.example.MiraiElectronics.repository.realization.OrderItem;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.repository.repositoryEnum.TransactionType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final TransactionService transactionService;

    public OrderService(OrderRepository orderRepository, OrderItemService orderItemService, SessionService sessionService, PaymentService paymentService, TransactionService transactionService) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.sessionService = sessionService;
        this.paymentService = paymentService;
        this.transactionService = transactionService;
    }


    @Transactional
    public ResponseEntity<?> makeOrder(OrderRequest orderRequest, HttpServletRequest request) {
        var user = sessionService.getFullUserFromSession(request);
        if (user == null)
            return ResponseEntity.ok("User not found");

        String shippingAddress = user.getAddress()!= null ? user.getAddress() : orderRequest.getShippingAddress();

        Order order = Order.builder()
                .user(user)
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

        transactionService.createTransaction(
                user,
                order.getTotalAmount(),
                "Payment for order #" + order.getOrderId(),
                TransactionType.PURCHASE
        );

        return ResponseEntity.ok(orderRepository.save(order));
    }


    public boolean isOrderSuccessful(){
        return paymentService.isPayed();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
    }
    
    public Order getOrderByIdAndUserId(Long orderId, User user) {
        return orderRepository.findById(orderId)
                .filter(order -> order.getUser().equals(user))
                .orElse(null);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findAllByCustomerId(user);
    }

    @Transactional
    public void deleteOrderById(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    @Transactional
    public void cancelOrder(Long orderId, User user) {
        Order order = getOrderByIdAndUserId(orderId, user);
        if (order == null)
            throw new IllegalStateException("Order doesn't exist");

        if (!order.getStatus().equals("PROCESSING") || order.getStatus().equals("PENDING"))
            throw new IllegalStateException("Невозможно отменить заказ в текущем статусе: " + order.getStatus());

        order.setStatus("CANCELLED");
        paymentService.refundToUser(order.getTotalAmount(),order.getUser());

        transactionService.createTransaction(
                user,
                order.getTotalAmount(),
                "Order #" + order.getOrderId() + " refund",
                TransactionType.REFUND
        );

        orderRepository.save(order);
    }

    @Transactional
    public void updateShippingAddress(Long orderId, String newAddress) {
        Order order = getOrderById(orderId);
        order.setShippingAddress(newAddress);
        orderRepository.save(order);
    }

    @Transactional
    public void updateOrderStatus(Long id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        orderRepository.save(order);
    }
}
