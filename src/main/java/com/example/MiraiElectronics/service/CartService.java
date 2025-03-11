package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.Cart;
import com.example.MiraiElectronics.repository.CartItem;
import com.example.MiraiElectronics.repository.CartRepository;
import com.example.MiraiElectronics.repository.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private  final CartItemService cartItemService;

    public CartService(CartRepository cartRepository, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
    }


    public Cart getCart(Long id){
        return  cartRepository.findById(id).orElseThrow();
    }

    public void addItem(Cart cart, Product product, int quantity){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        cartItemService.createCartItem(cartItem);
    }

}
