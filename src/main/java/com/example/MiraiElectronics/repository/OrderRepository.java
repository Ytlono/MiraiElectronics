package com.example.MiraiElectronics.repository;

import com.example.MiraiElectronics.repository.realization.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("SELECT o FROM Order o WHERE o.customerId = :customerId")
    List<Order> findAllByCustomerId(Long customerId);
}
