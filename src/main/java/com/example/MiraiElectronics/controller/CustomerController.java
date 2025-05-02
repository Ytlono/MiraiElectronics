package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.UpdateUserDataDTO;
import com.example.MiraiElectronics.dto.UserSessionDTO;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.Parser.UserParserService;
import com.example.MiraiElectronics.service.SessionService;
import com.example.MiraiElectronics.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class CustomerController {
    private final UserService userService;
    private final SessionService sessionService;

    public CustomerController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(HttpServletRequest request) {
        UserSessionDTO userSession = sessionService.getUserFromSession(request);
        if (userSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Пользователь не авторизован"));
        }
        
        User user = userService.findById(userSession.getId());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserData(@Valid @RequestBody UpdateUserDataDTO updateUserDataDTO, HttpServletRequest request) {
        UserSessionDTO userSession = sessionService.getUserFromSession(request);
        if (userSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Пользователь не авторизован"));
        }
        
        Long userId = userSession.getId();
        // Обновляем пользователя
        ResponseEntity<?> response = userService.updateUser(userId, updateUserDataDTO);
        
        // Получаем обновленного пользователя из базы данных
        User updatedUser = userService.findById(userId);
        
        // Обновляем пользователя в сессии
        sessionService.saveUserToSession(request, updatedUser);
        
        return response;
    }

    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteUser(HttpServletRequest request) {
        UserSessionDTO userSession = sessionService.getUserFromSession(request);
        if (userSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Пользователь не авторизован"));
        }
        
        return userService.deleteUser(userSession.getId());
    }
}
