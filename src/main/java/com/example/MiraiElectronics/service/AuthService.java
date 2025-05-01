package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.dto.AuthRequest;
import com.example.MiraiElectronics.dto.RegisterDTO;
import com.example.MiraiElectronics.repository.repositoryEnum.Role;
import com.example.MiraiElectronics.repository.realization.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserService userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterDTO registerRequest){
        if (userService.isUserExist(registerRequest.getEmail(),registerRequest.getUsername())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        String encodedPassword =passwordEncoder.encode(registerRequest.getPassword());

        User user = User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(encodedPassword)
                .role(Role.USER)
                .build();
        userService.createUser(user);
        return user;
    }

    public User login(AuthRequest authRequest) {
        User user = userService.findByUsername(authRequest.getUsername());

        if (user == null || !passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return null;
        }

        return user;
    }
}
