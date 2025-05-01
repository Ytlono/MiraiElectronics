package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.UpdateUserDataDTO;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.Parser.UserParserService;
import com.example.MiraiElectronics.service.SessionService;
import com.example.MiraiElectronics.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public class CustomerController {
    private final UserService userService;
    private final SessionService sessionService;

    public CustomerController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    public ResponseEntity<?> updateUserData(UpdateUserDataDTO updateUserDataDTO, HttpServletRequest request){
        Long userId = sessionService.getUserFromSession(request).getId();

        ResponseEntity<?> response = userService.updateUser(userId, updateUserDataDTO);

        User updatedUser = userService.findById(userId);

        sessionService.saveUserToSession(request, updatedUser);
        
        return response;
    }

    public ResponseEntity<?> deleteUser(HttpServletRequest request){
        return ResponseEntity.ok(1);
    }
}
