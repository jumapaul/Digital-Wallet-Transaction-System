package com.ditial_wallet.historyservice.repository;

import com.ditial_wallet.historyservice.entity.TransactionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<TransactionHistoryEntity, Long> {

    List<TransactionHistoryEntity> findAllByWalletId(Long walletId);

    List<TransactionHistoryEntity> findAllByUserId(Long userId);
}
