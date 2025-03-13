package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.repository.CartItem;
import com.example.MiraiElectronics.repository.Product;
import com.example.MiraiElectronics.service.CartService;
import com.example.MiraiElectronics.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CartController {
    private final CartService cartService;
    private final ProductService productService;

    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }


    @GetMapping("/cart")
    public String cart(Model model){
        model.addAttribute("cart",cartService.getCart(1L));
        return "cart";
    }

    @PostMapping("/cart/addToCart/{productId}")
    public String addToCart(@PathVariable Long productId){
        Product product = productService.findById(productId);
        return "";
    }

}
