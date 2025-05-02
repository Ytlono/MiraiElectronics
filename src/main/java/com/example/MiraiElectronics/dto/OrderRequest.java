package com.example.MiraiElectronics.dto;

import com.example.MiraiElectronics.repository.realization.OrderItem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotBlank(message = "Статус заказа не может быть пустым")
    private String status;
    
//    @NotBlank(message = "Адрес доставки не может быть пустым")
//    @Size(max = 200, message = "Адрес доставки не может содержать более 200 символов")
    private String shippingAddress;

    @NotEmpty(message = "Список товаров не может быть пустым")
    private List<Long> itemsId;
}
