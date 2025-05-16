package com.example.MiraiElectronics.listeners.Reminders;

import com.example.MiraiElectronics.events.AbandonedCartDetectedEvent;
import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.CartItemService;
import com.example.MiraiElectronics.service.JavaMailSenderService;
import org.mapstruct.control.MappingControl;
import org.springframework.context.event.EventListener;

public class CartReminderListener extends BaseReminderListener{

    public CartReminderListener(CartItemService cartItemService, JavaMailSenderService mailSenderService) {
        super(cartItemService, mailSenderService);
    }

    @EventListener
    public void onAbandonedCartReminder(AbandonedCartDetectedEvent event){
        CartItem item = event.getItem();
        User user = item.getCart().getUser();

        mailSenderService.send(
                user.getEmail(),
                "Вы забыли товар в корзине",
                String.format("Товар '%s' всё ещё ждёт вас в корзине!", item.getProduct().getName())
        );
    }
}
