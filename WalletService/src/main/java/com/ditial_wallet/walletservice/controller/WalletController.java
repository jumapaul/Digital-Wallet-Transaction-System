package com.ditial_wallet.walletservice.controller;

import com.ditial_wallet.walletservice.dtos.CreateWalletRequest;
import com.ditial_wallet.walletservice.dtos.TransactionRequest;
import com.ditial_wallet.walletservice.entity.Wallet;
import com.ditial_wallet.walletservice.service.OptimisticTransactionTest;
import com.ditial_wallet.walletservice.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final OptimisticTransactionTest optimisticTransactionTest;

    @PostMapping("wallet")
    public ResponseEntity<Wallet> createWallet(@RequestBody CreateWalletRequest request) {
        return ResponseEntity.ok(walletService.createWallet(request));
    }

    @PostMapping("wallet/fund")
    public ResponseEntity<Wallet> fundWallet(@RequestBody TransactionRequest request) throws InterruptedException {
        return ResponseEntity.ok(walletService.fundWallet(request));
    }

    @PostMapping("wallet/{walletId}/fund")
    public ResponseEntity<Wallet> transferFunds(
            @PathVariable(name = "walletId") Long walletId,
            @RequestBody TransactionRequest request) {

        return ResponseEntity.ok(walletService.transferFunds(request, walletId));
    }

    @GetMapping("wallet/{walletId}")
    public ResponseEntity<Wallet> getWallet(@PathVariable(name = "walletId") Long walletId) {
        return ResponseEntity.ok(walletService.getWallet(walletId));
    }

    @GetMapping("user/{userId}/wallet")
    public ResponseEntity<List<Wallet>> getUserWallets(@PathVariable(name = "userId") Long userId) {
        return ResponseEntity.ok(walletService.getUserWallets(userId));
    }
}
