package com.ditial_wallet.walletservice.repository;

import com.ditial_wallet.walletservice.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUserId(Long userId);

    List<Wallet> findAllByUserId(Long userId);
}
