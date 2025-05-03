package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.ICardRepository;
import com.example.MiraiElectronics.repository.realization.Card;
import com.example.MiraiElectronics.repository.realization.Cart;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CardService {
    public final ICardRepository cardRepository;

    public CardService(ICardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public ResponseEntity<?> addCart(){
        Card card = Card.builder().build();
        cardRepository.save(card);
        return ResponseEntity.ok(card);
    }

    public ResponseEntity<?> deleteCart(Long id) {
        cardRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No card with id " + id));

        cardRepository.deleteById(id);
        return ResponseEntity.ok("deleted");
    }

    public ResponseEntity<?> withdrawFromCard(Long id, BigDecimal sum) {
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No card with id " + id));

        if (checkBalance(sum,card)) {
            return ResponseEntity.badRequest().body("Insufficient funds");
        }

        card.setBalance(card.getBalance().subtract(sum));
        cardRepository.save(card);

        return ResponseEntity.ok("Withdrawal successful. New balance: " + card.getBalance());
    }

    public ResponseEntity<?> validCard(Card card){

        return ResponseEntity.ok(card);
    }

    public boolean checkBalance(BigDecimal sum, Card card) {
        if (sum.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Sum must be greater than zero");
        }

        return card.getBalance().compareTo(sum) >= 0;
    }

}
