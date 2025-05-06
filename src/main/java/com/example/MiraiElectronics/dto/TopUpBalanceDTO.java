package com.example.MiraiElectronics.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopUpBalanceDTO {
    @NotNull(message = "cardId должно быть указано")
    private Long cardId;

    @NotNull(message = "Сумма должна быть указана")
    private BigDecimal sum;
}

