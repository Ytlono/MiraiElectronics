package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.RegisterDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@SessionAttributes("pendingUser")
public class AuthController {

    @PostMapping("/register")
    public void register(@RequestBody RegisterDTO registerDTO){

    }

    @PostMapping("/confirmEmail")
    public void confirmEmail(){

    }

    @PostMapping("/login")
    public void login(){

    }

    @PostMapping("/logout")
    public void logout(){

    }

}
