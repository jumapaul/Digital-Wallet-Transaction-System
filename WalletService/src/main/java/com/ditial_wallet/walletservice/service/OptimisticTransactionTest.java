package com.ditial_wallet.walletservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OptimisticTransactionTest {
    private final WalletService walletService;

//    public void testOptimisticLocking(FundWalletRequest request) throws InterruptedException {
//        Thread th1 = new Thread(() -> {
//            try {
//                log.info("{} is attempting transaction", Thread.currentThread().getName());
//
//                Wallet wallet = walletService.fundWallet(request);
//
//                log.info("{} successfully booked the seat with version: {}", Thread.currentThread().getName(), wallet.getVersion());
//            } catch (Exception e) {
//                log.info("{} failed: {}", Thread.currentThread().getName(), e.getMessage());
//            }
//        });
//
//        Thread th2 = new Thread(() -> {
//            try {
//                log.info("{} is attempting transaction", Thread.currentThread().getName());
//
//                Wallet wallet = walletService.fundWallet(request);
//
//                log.info("{} successfully booked the seat with version: {}", Thread.currentThread().getName(), wallet.getVersion());
//            } catch (Exception e) {
//                log.info("{} failed: {}", Thread.currentThread().getName(), e.getMessage());
//            }
//        });
//
//        th1.start();
//        th2.start();
//        th1.join();
//        th2.join();
//    }
}
