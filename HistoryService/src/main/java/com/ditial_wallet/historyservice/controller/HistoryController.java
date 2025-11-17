package com.ditial_wallet.historyservice.controller;

import com.ditial_wallet.historyservice.entity.TransactionHistoryEntity;
import com.ditial_wallet.historyservice.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("/wallets/{walletId}/history")
    public ResponseEntity<List<TransactionHistoryEntity>> getWalletHistory(
            @PathVariable(name = "walletId") Long walletId
    ) {
        return ResponseEntity.ok(historyService.getWalletHistory(walletId));
    }

    @GetMapping("/users/{userId}/activity")
    public ResponseEntity<List<TransactionHistoryEntity>> getAllUserActivities(
            @PathVariable(name = "userId") Long userId
    ) {
        return ResponseEntity.ok(historyService.getUserActivities(userId));
    }
}
