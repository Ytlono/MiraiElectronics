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
public class CardController extends BaseController{
    private final CardService cardService;

    public CardController(SessionService sessionService, CardService cardService) {
        super(sessionService);
        this.cardService = cardService;
    }

    @PostMapping("/addCard")
    public ResponseEntity<?> addPaymentMethod(@RequestBody CardDTO cardDTO, HttpServletRequest request) {
        return ResponseEntity.ok(
                cardService.addCard(cardDTO,getFullUserOrThrow(request))
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCard(@RequestParam Long id, HttpServletRequest request) {
        return ResponseEntity.ok(
                cardService.deleteCard(id,getFullUserOrThrow(request))
        );
    }

    @GetMapping
    public ResponseEntity<?> getAllUserCards(HttpServletRequest request){
        return ResponseEntity.ok(
                cardService.getAllUserCards(getFullUserOrThrow(request))
        );
    }
}
