package com.ditial_wallet.walletservice.dtos;

public record ApiResponse<T>(
        String message,
        T data
) {
}
