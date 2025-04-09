package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.service.CartItemService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CartItemController {
    public final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/cart/{item_id}/increase")
    public String increaseProductsInCartItem(@PathVariable("item_id") Long itemId) {
        CartItem cartItem = cartItemService.getById(itemId);
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItem.setPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        cartItemService.updateCartItem(cartItem);
        return "redirect:/cart"; // Перенаправление обратно на страницу корзины
    }


}
