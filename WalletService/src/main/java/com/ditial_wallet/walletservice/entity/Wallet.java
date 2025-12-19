package com.ditial_wallet.walletservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Wallet table for list of all wallets
 */

@Entity(name = "Wallet")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private BigDecimal balance;
    @Version //for optimistic locking
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
