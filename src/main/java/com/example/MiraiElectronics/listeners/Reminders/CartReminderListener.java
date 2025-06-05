package com.example.MiraiElectronics.listeners.Reminders;

import com.example.MiraiElectronics.events.AbandonedCartDetectedEvent;
import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.CartItemService;
import com.example.MiraiElectronics.service.JavaMailSenderService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CartReminderListener extends BaseReminderListener {

    public CartReminderListener(CartItemService cartItemService, JavaMailSenderService mailSenderService) {
        super(cartItemService, mailSenderService);
    }

    @EventListener
    public void onAbandonedCartReminder(AbandonedCartDetectedEvent event) {
        CartItem item = event.getItem();
        User user = item.getCart().getUser();

        mailSenderService.send(
                user.getEmail(),
                "🛒 Напоминание: Товар ждет вас!",
                String.format("👋 Привет, %s!\n\nВы забыли товар '%s' в своей корзине. 🧺\n" +
                                "Не упустите шанс — он всё ещё ждёт вас! 😍\n\nПерейти в корзину 👉 [перейти]",
                        user.getUsername(),
                        item.getProduct().getName())
        );
    }
}
