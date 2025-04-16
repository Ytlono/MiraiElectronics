package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.AuthRequest;
import com.example.MiraiElectronics.dto.RegisterDTO;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.AuthService;
import com.example.MiraiElectronics.service.ConfirmationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@SessionAttributes("pendingUser")
public class AuthController {

    private final AuthService authService;
    private final ConfirmationService confirmationService;

    public AuthController(AuthService authService, ConfirmationService confirmationService) {
        this.authService = authService;
        this.confirmationService = confirmationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerRequest, HttpSession session) {
        if (registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Пароль не может быть пустым"));
        }

        confirmationService.sendConfirmationEmail(registerRequest.getEmail());
        session.setAttribute("pendingUser", registerRequest);

        return ResponseEntity.ok(Map.of("message", "Код подтверждения отправлен на email", "email", registerRequest.getEmail()));
    }

    @PostMapping("/confirmEmail")
    public void confirmEmail(@RequestParam String email,@RequestParam String code,@SessionAttribute("pendingUser") RegisterDTO pendingUser){
        if(!confirmationService.isConfirmed(email,code))
            return;
        confirmationService.removeConfirmedEmail(email);
        authService.register(pendingUser);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthRequest authRequest, HttpSession session){
        User user = authService.login(authRequest);
        if(user == null){
            ResponseEntity.ok("Incorrect");
        }
        session.setAttribute("pendingUser",user);
        ResponseEntity.ok(user);
    }

    @PostMapping("/logout")
    public void logout(){

    }

}
