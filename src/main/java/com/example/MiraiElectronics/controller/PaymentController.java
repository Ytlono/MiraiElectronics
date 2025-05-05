package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.CardDTO;
import com.example.MiraiElectronics.repository.realization.Transaction;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.service.CardService;
import com.example.MiraiElectronics.service.PaymentService;
import com.example.MiraiElectronics.service.SessionService;
import com.example.MiraiElectronics.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final CardService cardService;
    private final SessionService sessionService;
    private final PaymentService paymentService;
    private final TransactionService transactionService;

    public PaymentController(CardService cardService, SessionService sessionService, PaymentService paymentService, TransactionService transactionService) {
        this.cardService = cardService;
        this.sessionService = sessionService;
        this.paymentService = paymentService;
        this.transactionService = transactionService;
    }


    @PostMapping("/top-up")
    public ResponseEntity<?> topUpBalance(@RequestParam Long cardId,
                                          @RequestParam BigDecimal sum,
                                          HttpServletRequest request) {
        User user = sessionService.getFullUserFromSession(request);
        try {
            BigDecimal newBalance = paymentService.topUpUserBalanceFromCard(cardId, sum, user);
            return ResponseEntity.ok("Balance topped up successfully. New balance: " + newBalance);
        } catch (IllegalArgumentException | SecurityException e) {
            return ResponseEntity.badRequest().body("Top-up failed: " + e.getMessage());
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> getTransactionHistory(HttpServletRequest request) {
        List<Transaction> transactions = transactionService.getAllTransactions(sessionService.getFullUserFromSession(request));

        if (transactions.isEmpty())
            return ResponseEntity.ok("No transactions yet");

        return ResponseEntity.ok(transactions);
    }


    @PostMapping("/refund/{orderId}")
    public ResponseEntity<?> refundPayment(@PathVariable Long orderId,@PathVariable Long userId){
        return ResponseEntity.ok(1);
    }

    @PostMapping("/method")
    public ResponseEntity<?> addPaymentMethod(@RequestBody CardDTO cardDTO, HttpServletRequest request) {
        User user = sessionService.getFullUserFromSession(request);
        return ResponseEntity.ok(cardService.addCard(cardDTO,user));
    }
}
