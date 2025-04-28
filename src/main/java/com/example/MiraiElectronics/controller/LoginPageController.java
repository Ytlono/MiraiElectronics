package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.AuthRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class LoginPageController {
    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "expired", required = false) String expired,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Неверный логин или пароль");
        }

        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли из системы");
        }

        if (expired != null) {
            model.addAttribute("error", "Ваша сессия истекла. Пожалуйста, войдите снова");
        }

        model.addAttribute("loginRequest", new AuthRequest());
        return "login";
    }
}
