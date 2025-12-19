package com.ditial_wallet.walletservice.outbox;

import com.ditial_wallet.walletservice.dtos.TransactionStatus;
import com.ditial_wallet.walletservice.dtos.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "TransactionOutbox")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionOutBoxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private long walletId;
    private long userId;
    private BigDecimal amount;
    private long transactionId;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private LocalDateTime timestamp;
    private String topic;
    private boolean processed;
}
