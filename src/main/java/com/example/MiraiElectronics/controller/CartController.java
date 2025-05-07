package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.UserSessionDTO;
import com.example.MiraiElectronics.repository.realization.Cart;
import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.repository.realization.Product;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.CartItemService;
import com.example.MiraiElectronics.service.CartService;
import com.example.MiraiElectronics.service.ProductServices.ProductService;
import com.example.MiraiElectronics.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController extends BaseController{
    private final CartService cartService;
    private final ProductService productService;
    private final CartItemService cartItemService;

    public CartController(SessionService sessionService, CartService cartService, ProductService productService, CartItemService cartItemService) {
        super(sessionService);
        this.cartService = cartService;
        this.productService = productService;
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<?> getCart(HttpServletRequest request) {
        return ResponseEntity.ok(
                cartService.getCartByUser(
                        getFullUserOrThrow(request))
        );
    }

    @GetMapping("/items")
    public ResponseEntity<?> getCartItem(@RequestParam Long itemId, HttpServletRequest request) {
        return ResponseEntity.ok(
                cartItemService.getByIdForUser(
                        itemId,getFullUserOrThrow(request))
        );
    }

    @PostMapping("/items")
    public ResponseEntity<?> addToCart(@RequestParam Long productId, @RequestParam(defaultValue = "1") int quantity, HttpServletRequest request) {
        cartService.addItem(
                productService.findById(productId),
                quantity,
                getFullUserOrThrow(request)
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Товар добавлен в корзину"));
    }

    @DeleteMapping("/items")
    public ResponseEntity<?> removeFromCart(@RequestParam Long itemId, HttpServletRequest request) {
        cartItemService.removeItem(itemId, getFullUserOrThrow(request));
        return ResponseEntity.ok(Map.of("message", "Товар удален из корзины"));
    }

    @PutMapping("/items")
    public ResponseEntity<?> updateCartItemQuantity(@RequestParam Long itemId,
                                                    @RequestParam(defaultValue = "1") int delta,
                                                    HttpServletRequest request) {
        return ResponseEntity.ok(
                cartItemService.updateQuantity(
                        itemId, delta, getFullUserOrThrow(request))
        );
    }
}