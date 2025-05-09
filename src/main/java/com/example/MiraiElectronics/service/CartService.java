package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.realization.Cart;
import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.repository.CartRepository;
import com.example.MiraiElectronics.repository.realization.Product;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.Generic.GenericEntityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CartService extends GenericEntityService<Cart,Long> {
    private final CartRepository cartRepository;
    private final CartItemService cartItemService;

    public CartService(CartRepository cartRepository, CartItemService cartItemService) {
        super(cartRepository);
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
    }

    public Cart createCart(User user){
        Cart cart = Cart.builder()
                .user(user)
                .finalPrice(BigDecimal.ZERO)
                .build();
        cartRepository.save(cart);
        return cart;
    }

    public Cart getCartByUser(User user){
        return cartRepository.findByUser(user);
    }

    @Transactional
    public void addItem(Product product, int quantity, User user) {
        if (user == null)
            System.out.println("PISKI");
        Cart cart = getCartByUser(user);
        if (cart == null)
            System.out.println("SYYYYYYYYKA");
        CartItem cartItem = cartItemService.createCartItem(product, quantity, cart);
        cart.getCartItems().add(cartItem);
        cartRepository.save(cart);
    }
}
