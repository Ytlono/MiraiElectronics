package com.example.MiraiElectronics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDTO {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
