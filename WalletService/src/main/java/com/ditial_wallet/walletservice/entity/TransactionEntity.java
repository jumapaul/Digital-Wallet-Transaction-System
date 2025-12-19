package com.ditial_wallet.walletservice.entity;

import com.ditial_wallet.walletservice.dtos.TransactionStatus;
import com.ditial_wallet.walletservice.dtos.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction table to store all transactions
 */
@Entity(name = "transaction")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private TransactionStatus status;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private Wallet wallet;
}
