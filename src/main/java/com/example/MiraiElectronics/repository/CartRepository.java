package com.example.MiraiElectronics.repository;

import com.example.MiraiElectronics.repository.realization.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    public Optional<Cart> findByUserId(@Param("userId") Long userId);
}
