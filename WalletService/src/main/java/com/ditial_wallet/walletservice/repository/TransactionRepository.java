package com.ditial_wallet.walletservice.repository;

import com.ditial_wallet.walletservice.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
