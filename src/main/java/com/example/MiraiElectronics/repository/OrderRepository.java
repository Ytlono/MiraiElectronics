package com.example.MiraiElectronics.repository;

import com.example.MiraiElectronics.repository.realization.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
