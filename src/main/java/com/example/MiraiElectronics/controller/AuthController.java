package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.AuthRequest;
import com.example.MiraiElectronics.dto.RegisterDTO;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.AuthService;
import com.example.MiraiElectronics.service.ConfirmationService;
import com.example.MiraiElectronics.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@SessionAttributes("pendingUser")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final ConfirmationService confirmationService;
    private final SessionService sessionService;

    public AuthController(AuthService authService, ConfirmationService confirmationService, SessionService sessionService) {
        this.authService = authService;
        this.confirmationService = confirmationService;
        this.sessionService = sessionService;
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
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpSession session, HttpServletRequest request)
    {
        logger.info("Login attempt for user: {}", authRequest.getUsername());

        if (authRequest == null || authRequest.getUsername() == null || authRequest.getPassword() == null) {
            logger.error("Username or password not provided");
            return ResponseEntity.badRequest().body(Map.of("error", "Username and password must be provided"));
        }

        User user = authService.login(authRequest);
        if (user == null) {
            logger.error("Invalid username or password for user: {}", authRequest.getUsername());
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        sessionService.saveUserToSession(request, user); // правильный способ


        logger.info("Login successful for user: {}", authRequest.getUsername());
        logger.info("Session ID: {}", session.getId());

        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "user", user,
                "sessionId", session.getId()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }
}
