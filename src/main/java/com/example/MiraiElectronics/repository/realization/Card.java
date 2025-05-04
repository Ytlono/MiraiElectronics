package com.example.MiraiElectronics.repository.realization;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "card",schema = "public")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "masked_card_number",nullable = false)
    private String maskedCardNumber;

    @Column(name = "full_card_number_hash",nullable = false)
    private String fullCardNumberHash;

    @Column(name = "expiry_date",nullable = false)
    private String expiryDate;

    @Column(name = "cvv_hash", nullable = false)
    private String cvvHash;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

