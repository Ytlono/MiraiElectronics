package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final CategoryRepository categoryRepository;

    public MainController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/singup")
    public String singUp(Model model){
        model.addAttribute("title","Sing Up page");
        return "signup";
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("categories",categoryRepository.findAll());
        return "home";
    }
}
