package com.ditial_wallet.walletservice.service;

import com.ditial_wallet.walletservice.dtos.CreateWalletRequest;
import com.ditial_wallet.walletservice.dtos.TransactionRequest;
import com.ditial_wallet.walletservice.entity.Wallet;

import java.util.List;

public interface WalletService {

    Wallet createWallet(CreateWalletRequest request);

    Wallet fundWallet(TransactionRequest transactionRequest);

    Wallet transferFunds(TransactionRequest transactionRequest, Long walletId);

    Wallet getWallet(Long walletId);

    List<Wallet> getUserWallets(Long userId);
}
