package com.example.MiraiElectronics.schedulers;


import com.example.MiraiElectronics.events.AbandonedCartDetectedEvent;
import com.example.MiraiElectronics.repository.realization.CartItem;
import com.example.MiraiElectronics.service.CartItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class CartReminderScheduler {
    private final CartItemService cartItemService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${reminder.cart.threshold-minutes:1}")
    private long thresholdMinutes;

    public CartReminderScheduler(CartItemService cartItemService, ApplicationEventPublisher eventPublisher) {
        this.cartItemService = cartItemService;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void checkAbandonedCarts() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(thresholdMinutes);

        List<CartItem> staleItems = cartItemService.findByCreatedAtBefore(threshold);

        for (CartItem item : staleItems) {
            eventPublisher.publishEvent(new AbandonedCartDetectedEvent(item));
        }
    }
}
