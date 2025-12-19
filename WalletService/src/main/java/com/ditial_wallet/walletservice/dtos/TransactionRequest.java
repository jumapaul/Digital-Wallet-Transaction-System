package com.ditial_wallet.walletservice.dtos;

import java.math.BigDecimal;

public record TransactionRequest(
        Long walletId,
        BigDecimal amount
) {
}
