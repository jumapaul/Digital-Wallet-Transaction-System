package com.ditial_wallet.walletservice.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionOutBoxRepository extends JpaRepository<TransactionOutBoxEntity, Long> {

    List<TransactionOutBoxEntity> findAllByProcessedFalse();
}
