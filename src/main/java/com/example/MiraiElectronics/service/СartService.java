package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.realization.Cart;
import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.repository.CartRepository;
import com.example.MiraiElectronics.repository.realization.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class СartService {

    private final CartRepository cartRepository;

    public СartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart(Product product){

    }

    public void removeFromCart(CartItem CartItem){

    }

    public Cart getUserCart(Long id){
        Optional<Cart> temporaryCart = cartRepository.findByUserId(id);
        Cart cart = temporaryCart.get();
        return cart;
    }
}
