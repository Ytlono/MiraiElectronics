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
public class CustomerController extends BaseController{
    private final UserService userService;

    public CustomerController(SessionService sessionService, UserService userService) {
        super(sessionService);
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(HttpServletRequest request) {
        return ResponseEntity.ok(getFullUserOrThrow(request));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUserData(@Valid @RequestBody UpdateUserDataDTO updateUserDataDTO, HttpServletRequest request) {
        User userUpdate = userService.updateUser(getFullUserOrThrow(request), updateUserDataDTO);
        sessionService.saveUserToSession(request, userUpdate);
        return ResponseEntity.ok(userUpdate);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteUser(HttpServletRequest request) {
        return userService.deleteUser(getFullUserOrThrow(request));
    }
}
