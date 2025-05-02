package com.example.MiraiElectronics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;
    
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}