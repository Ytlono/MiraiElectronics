package com.example.MiraiElectronics.repository;

import com.example.MiraiElectronics.repository.realization.Card;
import com.example.MiraiElectronics.repository.realization.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card,Long> {
    @Query("SELECT c FROM Card c WHERE c.user = :user")
    List<Card> getAllUserCards(User user);
}
