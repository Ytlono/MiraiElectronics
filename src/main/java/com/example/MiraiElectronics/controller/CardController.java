package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.CardDTO;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.CardService;
import com.example.MiraiElectronics.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
public class CardController {
    private final SessionService sessionService;
    private final CardService cardService;

    public CardController(SessionService sessionService, CardService cardService) {
        this.sessionService = sessionService;
        this.cardService = cardService;
    }

    @PostMapping("/addCard")
    public ResponseEntity<?> addPaymentMethod(@RequestBody CardDTO cardDTO, HttpServletRequest request) {
        User user = sessionService.getFullUserFromSession(request);
        return ResponseEntity.ok(cardService.addCard(cardDTO,user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCard(@PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity.ok(
                cardService.deleteCard(id, sessionService.
                        getFullUserFromSession(request))
        );
    }

    @GetMapping
    public ResponseEntity<?> getAllUserCards(HttpServletRequest request){
        return ResponseEntity.ok(
                cardService.getAllUserCards(sessionService.
                        getFullUserFromSession(request))
        );
    }
}
