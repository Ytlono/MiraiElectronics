package com.example.MiraiElectronics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {

    @NotBlank(message = "Название бренда не может быть пустым")
    @Size(max = 255, message = "Название бренда должно быть не длиннее 255 символов")
    private String name;

    @Size(max = 255, message = "Страна происхождения должна быть не длиннее 255 символов")
    private String country;

    private String description;
}
