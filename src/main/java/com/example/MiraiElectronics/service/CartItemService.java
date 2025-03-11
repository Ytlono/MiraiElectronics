package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.CartItem;
import com.example.MiraiElectronics.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartItemService {
     private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void createCartItem(CartItem cartItem){
        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void updateCartItem(CartItem cartItem){

    }

}
