package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.realization.Cart;
import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.repository.CartItemRepository;
import com.example.MiraiElectronics.repository.realization.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem getById(Long id) {
        return cartItemRepository.findById(id).orElseThrow();
    }

    public List<CartItem> getAllById(List<Long> ids){
        return cartItemRepository.findAllById(ids);
    }

    @Transactional
    public CartItem createCartItem(Product product, int quantity, Cart cart) {
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public void deleteCartItem(Long id){
        cartItemRepository.deleteById(id);
    }
    
    @Transactional
    public void updateCartItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }
    
    @Transactional
    public void removeItem(Long itemId, Long userId) {
        CartItem cartItem = getById(itemId);
        deleteCartItem(itemId);

//        if (cartItem.getCart().getCartId().equals(userId)) {
//
//        } else {
//            throw new IllegalArgumentException("Товар не принадлежит корзине пользователя");
//        }
    }
    
    @Transactional
    public void updateQuantity(Long itemId, int quantity, Long userId) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество товара должно быть больше 0");
        }
        CartItem cartItem = getById(itemId);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity)));
        updateCartItem(cartItem);

//        if (cartItem.getCart().getCartId().equals(userId)) {
//
//        } else {
//            throw new IllegalArgumentException("Товар не принадлежит корзине пользователя");
//        }
    }
}
