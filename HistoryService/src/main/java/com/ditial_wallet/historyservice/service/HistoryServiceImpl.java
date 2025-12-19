package com.ditial_wallet.historyservice.service;

import com.ditial_wallet.historyservice.entity.TransactionHistoryEntity;
import com.ditial_wallet.historyservice.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;

    @Override
    public List<TransactionHistoryEntity> getWalletHistory(Long walletId) {
        return historyRepository.findAllByWalletId(walletId);
    }

    @Override
    public List<TransactionHistoryEntity> getUserActivities(Long userId) {
        return historyRepository.findAllByUserId(userId);
    }
}
