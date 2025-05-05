package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.TransactionRepository;
import com.example.MiraiElectronics.repository.realization.Transaction;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.repository.repositoryEnum.TransactionType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public ResponseEntity<?> addTransaction(Transaction transaction){
        return ResponseEntity.ok(transactionRepository.save(transaction));
    }

    public List<Transaction> getAllTransactions(User user){
        return transactionRepository.getAllUserTransactions(user);
    }

    @Transactional
    public void createTransaction(User user, BigDecimal amount, String description, TransactionType type){
        Transaction transaction = Transaction.builder()
                .user(user)
                .type(type)
                .amount(amount)
                .description(description)
                .build();
        addTransaction(transaction);
    }
}
