package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.repository.CartItem;
import com.example.MiraiElectronics.service.CartItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class CartItemController {
    public final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PatchMapping("/cart/{item_id}/increase")
    public void increaseProductsInCartItem(@PathVariable("item_id") Long itemId) {
        CartItem cartItem = cartItemService.getById(itemId);
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemService.updateCartItem(cartItem);
    }

}
