package com.example.MiraiElectronics.repository.repositoryEnum;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {
    PENDING("Ожидает оплаты"),
    PAID("Оплачен"),
    PROCESSING("В обработке"),
    SHIPPED("Отправлен"),
    DELIVERED("Доставлен"),
    CANCELLED("Отменен"),
    RETURNED("Возвращен");

    private final String description;

    public String getDescription() {
        return description;
    }
}
