package com.example.MiraiElectronics.controller;

import com.example.MiraiElectronics.dto.CardDTO;
import com.example.MiraiElectronics.dto.TopUpBalanceDTO;
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
public class PaymentController extends BaseController{
    private final SessionService sessionService;
    private final PaymentService paymentService;
    private final TransactionService transactionService;

    public PaymentController(SessionService sessionService, SessionService sessionService1, PaymentService paymentService, TransactionService transactionService) {
        super(sessionService);
        this.sessionService = sessionService1;
        this.paymentService = paymentService;
        this.transactionService = transactionService;
    }

    @PostMapping("/top-up")
    public ResponseEntity<?> topUpBalance(@RequestBody TopUpBalanceDTO topUpBalanceDTO,
                                          HttpServletRequest request) {
        Long cardId = topUpBalanceDTO.getCardId();

        if (cardId == null)
            return ResponseEntity.badRequest().body("Card ID cannot be null");

        try {
            BigDecimal newBalance = paymentService.topUpUserBalanceFromCard(topUpBalanceDTO.getCardId(), topUpBalanceDTO.getSum(), getFullUserOrThrow(request));
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
}
