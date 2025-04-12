package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cart")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/{item_id}/update-quantity")
    public ResponseEntity<?> updateQuantity(
            @PathVariable("item_id") Long itemId,
            @RequestParam("delta") int delta) {

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



    @GetMapping("/{item_id}")
    public ResponseEntity<CartItem> getCartItem(@PathVariable("item_id") Long itemId) {
        CartItem cartItem = cartItemService.getById(itemId);
        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id){
        cartItemService.deleteCartItem(id);
        return ResponseEntity.ok("Deleted");
    }

}
