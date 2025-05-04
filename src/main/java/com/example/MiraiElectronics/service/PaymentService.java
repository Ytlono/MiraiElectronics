package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.realization.Card;
import com.example.MiraiElectronics.repository.realization.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private final CardService cardService;
    private final UserService userService;

    public PaymentService(CardService cardService, UserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    public boolean isPayed() {
        return true;
    }

    @Transactional
    public BigDecimal topUpUserBalanceFromCard(Long cardId, BigDecimal sum, User user) {
        Card card = cardService.getCardById(cardId);
        cardService.validateCardOwnership(card, user);

        if (!cardService.hasSufficientBalance(sum, card)) {
            throw new IllegalArgumentException("Insufficient funds on the card");
        }

        card.setBalance(card.getBalance().subtract(sum));
        user.setBalance(user.getBalance().add(sum));

        cardService.saveCard(card);
        userService.saveUser(user);

        return user.getBalance();
    }
}

