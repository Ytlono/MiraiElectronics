package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.realization.Cart;
import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.repository.CartRepository;
import com.example.MiraiElectronics.repository.realization.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;

    public CartService(CartRepository cartRepository, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
    }

    public Cart getCart(Long id) {
        return cartRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void addItem(Product product, int quantity, Long cartId) {
        Cart cart = getCart(cartId);
        CartItem cartItem = cartItemService.createCartItem(product, quantity, cart);
        cart.getCartItems().add(cartItem);
        cartRepository.save(cart);
    }
}
