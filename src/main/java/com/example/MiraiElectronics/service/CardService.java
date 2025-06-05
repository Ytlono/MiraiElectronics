package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.dto.CardDTO;
import com.example.MiraiElectronics.repository.CardRepository;
import com.example.MiraiElectronics.repository.realization.Card;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.Generic.GenericEntityService;
import com.example.MiraiElectronics.Parser.CardParserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CardService extends GenericEntityService<Card,Long> {
    private final CardRepository cardRepository;
    private final CardParserService cardParserService;
    private final PasswordEncoder passwordEncoder;

    public CardService(CardRepository cardRepository, CardParserService cardParserService, PasswordEncoder passwordEncoder) {
        super(cardRepository);
        this.cardRepository = cardRepository;
        this.cardParserService = cardParserService;
        this.passwordEncoder = passwordEncoder;
    }

    public Card addCard(CardDTO cardDTO, User user) {
        validateCard(cardDTO);

        Card card = Card.builder()
                .maskedCardNumber(cardParserService.maskCardNumber(cardDTO.getFullCardNumber()))
                .fullCardNumberHash(passwordEncoder.encode(cardDTO.getFullCardNumber()))
                .cvvHash(passwordEncoder.encode(cardDTO.getCvv()))
                .expiryDate(cardDTO.getExpiryDate())
                .balance(cardDTO.getBalance() != null ? cardDTO.getBalance() : BigDecimal.ZERO)
                .user(user)
                .build();

        return cardRepository.save(card);
    }

    public boolean verifyCard(Card card, String inputCardNumber, String inputCVV) {
        boolean cardNumberMatch = passwordEncoder.matches(inputCardNumber, card.getFullCardNumberHash());
        boolean cvvMatch = passwordEncoder.matches(inputCVV, card.getCvvHash());

        return cardNumberMatch && cvvMatch;
    }

    public List<Card> getAllUserCards(User user){
        return cardRepository.getAllUserCards(user);
    }

    public void deleteCard(Long cardId, User currentUser) {
        Card card = findById(cardId);
        validateCardOwnership(card, currentUser);
        cardRepository.delete(card);
    }

    @Transactional
    public BigDecimal withdrawFromCard(Long cardId, BigDecimal amount, User currentUser) {
        Card card = findById(cardId);
        validateCardOwnership(card, currentUser);
        validateSufficientBalance(amount, card);

        card.setBalance(card.getBalance().subtract(amount));
        save(card);

        return amount;
    }

    public void validateCard(CardDTO cardDTO) {
        if(!cardParserService.isValidCardNumber(cardDTO.getFullCardNumber()))
            throw new IllegalArgumentException("Incorrect card number format");

        if (!cardParserService.isValidCVV(cardDTO.getCvv()))
            throw new IllegalArgumentException("Incorrect card cvv format");

        if(!cardParserService.isValidExpiryDate(cardDTO.getExpiryDate()))
            throw new IllegalArgumentException("Incorrect card expiry date format");
    }


    public void validateSufficientBalance(BigDecimal sum, Card card) {
        if (sum.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Сумма должна быть больше нуля");

        if (card.getBalance().compareTo(sum) < 0)
            throw new IllegalArgumentException("Недостаточно средств на карте для проведения операции");
    }

    public void validateCardOwnership(Card card, User user) {
        if (!card.getUser().getUserId().equals(user.getUserId()))
            throw new SecurityException("Access denied: this card doesn't belong to the current user.");
    }
}
