package com.example.MiraiElectronics.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionDTO {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
