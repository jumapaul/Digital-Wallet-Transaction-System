package com.ditial_wallet.walletservice.dtos;

import java.math.BigDecimal;

/**
 * Request body for making transaction such as funding and wallet transfer.
 */
public record TransactionRequest(
        Long walletId,
        BigDecimal amount
) {
}
