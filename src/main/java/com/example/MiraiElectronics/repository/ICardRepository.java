package com.example.MiraiElectronics.repository;

import com.example.MiraiElectronics.repository.realization.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICardRepository extends JpaRepository<Card,Long> {
}
