package com.example.MiraiElectronics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressDTO {
    @NotBlank(message = "Улица не может быть пустой")
    @Size(max = 100, message = "Улица не может содержать более 100 символов")
    private String street;
    
    @NotBlank(message = "Город не может быть пустым")
    @Size(max = 50, message = "Город не может содержать более 50 символов")
    private String city;
    
    @NotBlank(message = "Область/Штат не может быть пустым")
    @Size(max = 50, message = "Область/Штат не может содержать более 50 символов")
    private String state;
    
    @NotBlank(message = "Почтовый индекс не может быть пустым")
    @Pattern(regexp = "^[0-9]{5,10}$", message = "Некорректный формат почтового индекса")
    private String postalCode;
    
    @NotBlank(message = "Страна не может быть пустой")
    @Size(max = 50, message = "Страна не может содержать более 50 символов")
    private String country;
}
