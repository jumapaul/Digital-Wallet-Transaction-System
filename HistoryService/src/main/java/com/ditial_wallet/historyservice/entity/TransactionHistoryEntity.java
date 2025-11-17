package com.ditial_wallet.historyservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long walletId;
    private Long userId;
    private BigDecimal amount;
    private String eventType;
    private Long transactionId;
    private LocalDateTime createdAt;
}


