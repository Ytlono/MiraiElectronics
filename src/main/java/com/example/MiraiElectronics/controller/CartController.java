package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.repository.realization.Product;
import com.example.MiraiElectronics.service.CartItemService;
import com.example.MiraiElectronics.service.CartService;
import com.example.MiraiElectronics.service.ProductServices.ProductService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {
    private final CartService cartService;
    private final ProductService productService;
    private final CartItemService cartItemService;

    public CartController(CartService cartService, ProductService productService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.productService = productService;
        this.cartItemService = cartItemService;
    }


    @GetMapping("/cart")
    public String cart(Model model){
        model.addAttribute("cart",cartService.getCart(1L));
        return "cart";
    }

    @PostMapping("/cart/addToCart/{productId}")
    public String addToCart(@PathVariable Long productId) {
        Product product = productService.findById(productId);
        cartService.addItem(product, 1, 1L); // Добавляем сразу через сервис
        return "redirect:/cart";
    }
}
