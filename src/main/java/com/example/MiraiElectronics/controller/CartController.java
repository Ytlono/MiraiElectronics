package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String cart(Model model){
        model.addAttribute("cart",cartService.getCart(1L));
        return "cart";
    }



}
