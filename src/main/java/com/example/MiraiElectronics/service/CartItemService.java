package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.realization.Cart;
import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.repository.CartItemRepository;
import com.example.MiraiElectronics.repository.realization.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem getById(Long id) {
        return cartItemRepository.findById(id).orElseThrow();
    }

    public CartItem createCartItem(Product product, int quantity, Cart cart) {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public void updateCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }
}
