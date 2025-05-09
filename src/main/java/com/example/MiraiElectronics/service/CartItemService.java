package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.CartItemRepository;
import com.example.MiraiElectronics.repository.realization.*;
import com.example.MiraiElectronics.service.Generic.GenericEntityService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartItemService extends GenericEntityService<CartItem,Long> {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        super(cartItemRepository);
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem getByIdForUser(Long id,User user){
        CartItem cartItem = findById(id);
        isCartOwnedByUser(cartItem,user);
        return cartItem;
    }

    public List<CartItem> getAllById(List<Long> ids){
        return repository.findAllById(ids);
    }

    @Transactional
    public CartItem createCartItem(Product product, int quantity, Cart cart) {
        CartItem cartItem = CartItem.builder()
                .product(product)
                .quantity(quantity)
                .price(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .cart(cart)
                .build();
        return save(cartItem);
    }

    @Transactional
    public CartItem updateCartItem(CartItem cartItem) {
        return save(cartItem);
    }
    
    @Transactional
    public void removeItem(Long itemId, User user) {
        CartItem cartItem = findById(itemId);

        isCartOwnedByUser(cartItem,user);

        delete(cartItem);
    }
    
    @Transactional
    public CartItem updateQuantity(Long itemId, int quantity, User user) {
        CartItem cartItem = findById(itemId);
        isCartOwnedByUser(cartItem,user);

        if (quantity <= 0)
            throw new IllegalArgumentException("Количество товара должно быть больше 0");

        cartItem.setQuantity(quantity);
        cartItem.setPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity)));
        return updateCartItem(cartItem);
    }

    private void isCartOwnedByUser(CartItem cartItem, User user){
        if (!cartItem.getCart().getUser().equals(user)) {
            throw new IllegalArgumentException("Товар не принадлежит корзине пользователя");
        }
    }
}
