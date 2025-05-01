package com.example.MiraiElectronics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDataDTO {
    private String username;
    private String phoneNumber;
    private AddressDTO address;
}
