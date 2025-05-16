package com.example.MiraiElectronics.repository;

import com.example.MiraiElectronics.repository.realization.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Query("""
    SELECT DISTINCT ci.cart.user.email 
    FROM CartItem ci 
    WHERE ci.product.productId = :productId
""")
    List<String> findUserEmailsByProductId(@Param("productId") Long productId);

    List<CartItem> findByCreatedAtBefore(LocalDateTime threshold);

}
