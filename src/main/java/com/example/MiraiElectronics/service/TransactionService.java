package com.example.MiraiElectronics.service;

import com.example.MiraiElectronics.repository.TransactionRepository;
import com.example.MiraiElectronics.repository.realization.Transaction;
import com.example.MiraiElectronics.repository.realization.User;
import com.example.MiraiElectronics.repository.repositoryEnum.TransactionType;
import com.example.MiraiElectronics.service.Generic.GenericEntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService extends GenericEntityService<Transaction,Long> {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
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
        save(transaction);
    }
}
