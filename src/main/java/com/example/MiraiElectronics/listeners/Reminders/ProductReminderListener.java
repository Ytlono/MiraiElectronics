package com.example.MiraiElectronics.listeners.Reminders;

import com.example.MiraiElectronics.events.LowStockEvent;
import com.example.MiraiElectronics.events.ProductPriceChangedEvent;
import com.example.MiraiElectronics.service.CartItemService;
import com.example.MiraiElectronics.service.JavaMailSenderService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductReminderListener extends BaseReminderListener{


    public ProductReminderListener(JavaMailSenderService mailSenderService,CartItemService cartItemService) {
        super(cartItemService,mailSenderService);
    }

    @EventListener
    public void onProductPriceChange(ProductPriceChangedEvent event) {
        for(var email:getNecessaryEmails(event.getProductId())) {
            mailSenderService.send(
                    email,
                    "Изменение цены продукта",
                    String.format("Цена продукта с ID %d изменилась с %s на %s",
                            event.getProductId(), event.getOldPrice(), event.getNewPrice())
            );
        }
    }

    @EventListener
    public void onProductLowStock(LowStockEvent event) {
        for(var email:getNecessaryEmails(event.getProductId())) {
            mailSenderService.send(
                    email,
                    "Изменение цены продукта",
                    String.format("Успейте купить!\n" +
                                    "Товара: %s " +
                                    "осталось меньше %s",
                            event.getProductName(), event.getCurrentStock())
            );
        }
    }

}

