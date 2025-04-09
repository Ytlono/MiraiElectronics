package com.example.MiraiElectronics.repository;

import com.example.MiraiElectronics.repository.realization.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
