package com.example.MiraiElectronics.repository;

import com.example.MiraiElectronics.repository.realization.Order;
import com.example.MiraiElectronics.repository.realization.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUser(User user);
}
