package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.dto.CardDTO;
import com.example.MiraiElectronics.repository.CardRepository;
import com.example.MiraiElectronics.repository.realization.Card;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.Parser.CardParserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final CardParserService cardParserService;

    public CardService(CardRepository cardRepository, CardParserService cardParserService) {
        this.cardRepository = cardRepository;
        this.cardParserService = cardParserService;
    }

    public ResponseEntity<?> addCard(CardDTO cardDTO, User user) {
        validCard(cardDTO);

        String maskedNumber = cardParserService.maskCardNumber(cardDTO.getFullCardNumber());
        String cardHash = String.valueOf(cardDTO.getFullCardNumber().hashCode());
        String cvvHash = String.valueOf(cardDTO.getCvv().hashCode());

        Card card = Card.builder()
                .maskedCardNumber(maskedNumber)
                .fullCardNumberHash(cardHash)
                .cvvHash(cvvHash)
                .expiryDate(cardDTO.getExpiryDate())
                .balance(cardDTO.getBalance() != null ? cardDTO.getBalance() : BigDecimal.ZERO)
                .user(user)
                .build();
        System.out.println("Saving card: " + card);
        cardRepository.save(card);
        return ResponseEntity.ok(card);
    }

    public List<Card> getAllUserCards(User user){
        return cardRepository.getAllUserCards(user);
    }

    public ResponseEntity<?> deleteCard(Long cardId, User currentUser) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new IllegalStateException("No card with id " + cardId));

        if (!card.getUser().getUserId().equals(currentUser.getUserId())) {
            throw new SecurityException("Access denied: this card doesn't belong to the current user.");
        }

        cardRepository.delete(card);
        return ResponseEntity.ok("Card deleted");
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

    public ResponseEntity<?> validCard(CardDTO cardDTO){
        if(!cardParserService.isValidCardNumber(cardDTO.getFullCardNumber())){
            throw new IllegalArgumentException("Incorrect card number format");
        }

        if (!cardParserService.isValidCVV(cardDTO.getCvv())){
            throw new IllegalArgumentException("Incorrect card cvv format");
        }

        if(!cardParserService.isValidExpiryDate(cardDTO.getExpiryDate())){
            throw new IllegalArgumentException("Incorrect card expireDate format");
        }
        return ResponseEntity.ok(cardDTO);
    }

    public boolean checkBalance(BigDecimal sum, Card card) {
        if (sum.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Sum must be greater than zero");
        }

        return card.getBalance().compareTo(sum) >= 0;
    }

}
