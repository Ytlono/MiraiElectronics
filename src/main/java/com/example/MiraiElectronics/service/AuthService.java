package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.dto.AuthRequest;
import com.example.MiraiElectronics.dto.RegisterDTO;
import com.example.MiraiElectronics.repository.repositoryEnum.Role;
import com.example.MiraiElectronics.repository.realization.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final CartService cartService;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, CartService cartService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
    }


    public void register(RegisterDTO registerRequest){
        User user = User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .balance(BigDecimal.ZERO)
                .role(Role.USER)
                .build();

        userService.saveUser(user);
        cartService.createCart(user);
    }

    public User login(AuthRequest authRequest) {
        User user = userService.findByUsername(authRequest.getUsername());

        if (user == null || !passwordEncoder.matches(authRequest.getPassword(), user.getPassword()))
            return null;

        return user;
    }
}
