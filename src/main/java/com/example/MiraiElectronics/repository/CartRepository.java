package com.example.MiraiElectronics.repository;

import com.example.MiraiElectronics.repository.realization.Cart;
import com.example.MiraiElectronics.repository.realization.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query("ELECT o FROM Cart o WHERE o.user = :user")
    public Optional<Cart> findByUser(User user);
}
