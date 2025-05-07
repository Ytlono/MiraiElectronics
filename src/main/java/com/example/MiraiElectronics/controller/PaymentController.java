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
public class PaymentController extends BaseController {
    private final PaymentService paymentService;
    private final TransactionService transactionService;

    public PaymentController(SessionService sessionService, PaymentService paymentService, TransactionService transactionService) {
        super(sessionService);
        this.paymentService = paymentService;
        this.transactionService = transactionService;
    }

    @PostMapping("/top-up")
    public ResponseEntity<?> topUpBalance(@RequestBody TopUpBalanceDTO dto, HttpServletRequest request) {
        if (dto.getCardId() == null)
            return ResponseEntity.badRequest().body("Card ID cannot be null");

        var balance = paymentService.topUpUserBalanceFromCard(
                dto.getCardId(), dto.getSum(), getFullUserOrThrow(request)
        );
        return ResponseEntity.ok("Balance topped up successfully. New balance: " + balance);
    }


    @GetMapping("/history")
    public ResponseEntity<?> getTransactionHistory(HttpServletRequest request) {
        var transactions = transactionService.getAllTransactions(getFullUserOrThrow(request));
        return ResponseEntity.ok(transactions);
    }
}

