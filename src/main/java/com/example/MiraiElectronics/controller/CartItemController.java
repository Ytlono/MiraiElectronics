package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.service.CartItemService;
import com.example.MiraiElectronics.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {
    private final CartItemService cartItemService;
    private final SessionService sessionService;

    public CartItemController(CartItemService cartItemService, SessionService sessionService) {
        this.cartItemService = cartItemService;
        this.sessionService = sessionService;
    }

    @PostMapping("/update-quantity")
    public ResponseEntity<?> updateQuantity(
            @RequestParam Long itemId,
            @RequestParam(defaultValue = "1") int delta, HttpServletRequest request) {

        CartItem cartItem = cartItemService.getById(itemId);
        int newQuantity = cartItem.getQuantity() + delta;

        if (newQuantity <= 0) {
            return ResponseEntity.badRequest().body("Quantity cannot be less than 1");
        }

        cartItem.setQuantity(newQuantity);
        cartItem.setPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(newQuantity)));
        cartItemService.updateCartItem(cartItem);
        return ResponseEntity.ok(cartItem);
    }



    @GetMapping("/item")
    public ResponseEntity<CartItem> getCartItem(@RequestParam Long itemId) {
        CartItem cartItem = cartItemService.getById(itemId);
        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCartItem(@RequestParam Long id){
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok("Deleted");
    }

}
