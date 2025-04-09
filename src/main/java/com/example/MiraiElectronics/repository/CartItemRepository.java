package com.example.MiraiElectronics.repository;

import com.example.MiraiElectronics.repository.realization.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
