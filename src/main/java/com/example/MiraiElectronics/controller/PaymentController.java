package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.CardDTO;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.CardService;
import com.example.MiraiElectronics.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final CardService cardService;
    private final SessionService sessionService;

    public PaymentController(CardService cardService, SessionService sessionService) {
        this.cardService = cardService;
        this.sessionService = sessionService;
    }

    @PostMapping("/top-up")
    public ResponseEntity<?> topUpBalance(){
        return ResponseEntity.ok(1);
    }

    @GetMapping("/balance")
    public ResponseEntity<?> getUserBalance(){
        return ResponseEntity.ok(1);
    }

    @PostMapping("/order/{orderId}")
    public ResponseEntity<?> payForOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(1);
    }

    @GetMapping("/history")
    public ResponseEntity<?> getTransactionHistory(){
        return ResponseEntity.ok(1);
    }

    @PostMapping("/refund/{orderId}")
    public ResponseEntity<?> refundPayment(@PathVariable Long orderId){
        return ResponseEntity.ok(1);
    }

    @PostMapping("/method")
    public ResponseEntity<?> addPaymentMethod(@RequestBody CardDTO cardDTO, HttpServletRequest request) {
        User user = sessionService.getFullUserFromSession(request);
        return ResponseEntity.ok(cardService.addCard(cardDTO,user));
    }

    @GetMapping("/status/{paymentId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String paymentId){
        return ResponseEntity.ok(1);
    }

}
