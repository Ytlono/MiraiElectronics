package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.UserSessionDTO;
import com.example.MiraiElectronics.repository.realization.Cart;
import com.example.MiraiElectronics.repository.realization.Product;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.CartItemService;
import com.example.MiraiElectronics.service.CartService;
import com.example.MiraiElectronics.service.ProductServices.ProductService;
import com.example.MiraiElectronics.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final CartItemService cartItemService;
    private final SessionService sessionService;

    public CartController(CartService cartService, ProductService productService, CartItemService cartItemService, SessionService sessionService) {
        this.cartService = cartService;
        this.productService = productService;
        this.cartItemService = cartItemService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<?> getCart(HttpServletRequest request) {
        UserSessionDTO user = sessionService.getUserFromSession(request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Пользователь не авторизован"));
        }
        
        Cart cart = cartService.getCart(user.getId());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items/{productId}")
    public ResponseEntity<?> addToCart(@PathVariable Long productId, @RequestParam(defaultValue = "1") int quantity, HttpServletRequest request) {
        User user = sessionService.getFullUserFromSession(request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Пользователь не авторизован"));
        }
        
        Product product = productService.findById(productId);
        cartService.addItem(product, quantity, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Товар добавлен в корзину"));
    }
    
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long itemId, HttpServletRequest request) {
        UserSessionDTO user = sessionService.getUserFromSession(request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Пользователь не авторизован"));
        }
        
        cartItemService.removeItem(itemId, user.getId());
        return ResponseEntity.ok(Map.of("message", "Товар удален из корзины"));
    }
    
    @PutMapping("/items/{itemId}")
    public ResponseEntity<?> updateCartItemQuantity(@PathVariable Long itemId, @RequestParam int quantity, HttpServletRequest request) {
        UserSessionDTO user = sessionService.getUserFromSession(request);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Пользователь не авторизован"));
        }
        
        cartItemService.updateQuantity(itemId, quantity, user.getId());
        return ResponseEntity.ok(Map.of("message", "Количество товара обновлено"));
    }
}
