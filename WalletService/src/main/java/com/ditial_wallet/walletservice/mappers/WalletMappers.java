package com.ditial_wallet.walletservice.mappers;

import com.ditial_wallet.walletservice.dtos.*;
import com.ditial_wallet.walletservice.entity.TransactionEntity;
import com.ditial_wallet.walletservice.entity.Wallet;
import com.ditial_wallet.walletservice.outbox.TransactionOutBoxEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Mapper class to map entities and responses
 */

@Service
public class WalletMappers {

    public Wallet toWallet(CreateWalletRequest request) {
        return Wallet.builder()
                .userId(request.userId())
                .balance(BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();
    }

    public TransactionEntity toTransaction(
            TransactionRequest transactionRequest,
            Wallet wallet, TransactionStatus status, TransactionType type) {
        return TransactionEntity.builder()
                .amount(transactionRequest.amount())
                .type(type)
                .status(status)
                .createdAt(LocalDateTime.now())
                .wallet(wallet)
                .build();

    }

    public TransactionOutBoxEntity toTransactionOutboxEntity(
            Wallet wallet,
            TransactionEntity transactionEntity,
            String topic
    ) {
        return TransactionOutBoxEntity.builder()
                .transactionType(transactionEntity.getType())
                .walletId(wallet.getId())
                .userId(wallet.getUserId())
                .amount(transactionEntity.getAmount())
                .transactionId(transactionEntity.getId())
                .status(transactionEntity.getStatus())
                .timestamp(transactionEntity.getCreatedAt())
                .topic(topic)
                .processed(false)
                .build();
    }
}
