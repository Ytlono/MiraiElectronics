package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.AuthRequest;
import com.example.MiraiElectronics.dto.RegisterDTO;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.AuthService;
import com.example.MiraiElectronics.service.ConfirmationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@SessionAttributes("pendingUser")
public class AuthController {

    private final AuthService authService;
    private final ConfirmationService confirmationService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, ConfirmationService confirmationService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.confirmationService = confirmationService;
        this.authenticationManager = authenticationManager;
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
    public ResponseEntity<?> confirmEmail(@RequestParam String email, @RequestParam String code, @SessionAttribute("pendingUser") RegisterDTO pendingUser) {
        if (!confirmationService.isConfirmed(email, code)) {
            return ResponseEntity.ok("discard");
        }
        confirmationService.removeConfirmedEmail(email);
        authService.register(pendingUser);
        return ResponseEntity.ok(pendingUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpSession session) {
        User user = authService.login(authRequest);
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setAttribute("pendingUser", user);

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "user", user
        ));
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }
}
