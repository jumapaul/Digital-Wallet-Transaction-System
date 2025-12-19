package com.ditial_wallet.historyservice.service;

import com.ditial_wallet.historyservice.entity.TransactionHistoryEntity;

import java.util.List;

public interface HistoryService {

    List<TransactionHistoryEntity> getWalletHistory(Long walletId);

    List<TransactionHistoryEntity> getUserActivities(Long userId);
}
