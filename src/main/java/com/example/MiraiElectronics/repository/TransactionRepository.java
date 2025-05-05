package com.example.MiraiElectronics.repository;

import com.example.MiraiElectronics.repository.realization.Card;
import com.example.MiraiElectronics.repository.realization.Transaction;
import com.example.MiraiElectronics.repository.realization.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query("SELECT c FROM Transaction c WHERE c.user = :user")
    List<Transaction> getAllUserTransactions(User user);
}
