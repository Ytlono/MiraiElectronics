package com.example.MiraiElectronics.listeners.Reminders;

import com.example.MiraiElectronics.service.CartItemService;
import com.example.MiraiElectronics.service.JavaMailSenderService;

import java.util.List;

public class BaseReminderListener {
    protected final CartItemService cartItemService;
    protected final JavaMailSenderService mailSenderService;

    public BaseReminderListener(CartItemService cartItemService, JavaMailSenderService mailSenderService) {
        this.cartItemService = cartItemService;
        this.mailSenderService = mailSenderService;
    }

    public List<String> getNecessaryEmails(Long id){
        return cartItemService.findUserEmailsByProductId(id);
    }
}
