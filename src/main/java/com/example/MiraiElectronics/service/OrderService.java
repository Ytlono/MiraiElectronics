package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.dto.OrderRequest;
import com.example.MiraiElectronics.events.OrderSatusUpdateEvent;
import com.example.MiraiElectronics.repository.OrderRepository;
import com.example.MiraiElectronics.repository.realization.Order;
import com.example.MiraiElectronics.repository.realization.OrderItem;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.repository.repositoryEnum.TransactionType;
import com.example.MiraiElectronics.service.Generic.GenericEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService extends GenericEntityService<Order,Long> {
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final PaymentService paymentService;
    private final TransactionService transactionService;
    private final DomainEventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository, OrderItemService orderItemService, PaymentService paymentService, TransactionService transactionService, DomainEventPublisher eventPublisher) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.paymentService = paymentService;
        this.transactionService = transactionService;
        this.eventPublisher = eventPublisher;
    }


    @Transactional
    public ResponseEntity<?> makeOrder(OrderRequest orderRequest,User user) {

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

        return ResponseEntity.ok(save(order));
    }

    public boolean isOrderSuccessful(){
        return paymentService.isPayed();
    }

    public Order getOrderByIdAndUserId(Long orderId, User user) {
        return orderRepository.findById(orderId)
                .filter(order -> order.getUser().equals(user))
                .orElse(null);
    }

    public List<Order> getUserOrders(User user) {
        return orderRepository.findAllByUser(user);
    }

    @Transactional
    public void cancelOrder(Long orderId, User user) {
        Order order = getOrderByIdAndUserId(orderId, user);

        updateOrderStatus(orderId,"CANCELLED");
        paymentService.refundToUser(order.getTotalAmount(),order.getUser());

        transactionService.createTransaction(
                user,
                order.getTotalAmount(),
                "Order #" + order.getOrderId() + " refund",
                TransactionType.REFUND
        );

        save(order);
    }

    @Transactional
    public Order updateShippingAddress(Long orderId, String newAddress) {
        Order order = findById(orderId);
        order.setShippingAddress(newAddress);
        return save(order);
    }

    @Transactional
    public Order updateOrderStatus(Long id, String status) {
        Order order = findById(id);
        order.setStatus(status);
        createOrderStatusUpdateEvent(order);
        return save(order);
    }

    private void createOrderStatusUpdateEvent(Order order){
        eventPublisher.publish(OrderSatusUpdateEvent.builder()
                .order(order)
                .build());
    }
}
