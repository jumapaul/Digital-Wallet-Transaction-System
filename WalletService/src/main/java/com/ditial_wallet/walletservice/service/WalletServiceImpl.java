package com.ditial_wallet.walletservice.service;

import com.ditial_wallet.walletservice.dtos.*;
import com.ditial_wallet.walletservice.entity.TransactionEntity;
import com.ditial_wallet.walletservice.exception.BadRequestException;
import com.ditial_wallet.walletservice.exception.NotFoundException;
import com.ditial_wallet.walletservice.entity.Wallet;
import com.ditial_wallet.walletservice.mappers.WalletMappers;
import com.ditial_wallet.walletservice.outbox.TransactionOutBoxRepository;
import com.ditial_wallet.walletservice.outbox.TransactionOutBoxEntity;
import com.ditial_wallet.walletservice.repository.TransactionRepository;
import com.ditial_wallet.walletservice.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final WalletMappers mappers;
    private final TransactionOutBoxRepository transactionOutBoxRepository;
    public static final String FundWalletTopic = "FUND_WALLET_EVENT";
    public static final String TransferWalletTopic = "TRANSFER_WALLET_EVENT";

    @Override
    public Wallet createWallet(CreateWalletRequest request) {

        return walletRepository.save(mappers.toWallet(request));
    }

    @Transactional
    @Override
    public Wallet fundWallet(TransactionRequest transactionRequest) {
        Wallet wallet = walletRepository.findById(transactionRequest.walletId()).orElseThrow(() ->
                new NotFoundException("Wallet with id " + transactionRequest.walletId() + " not found")
        );
        try {
            if (transactionRequest.amount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BadRequestException("Cannot fund less than 1");
            }
            wallet.setBalance(wallet.getBalance().add(transactionRequest.amount()));
            wallet.setUpdatedAt(LocalDateTime.now());

            TransactionEntity transactionEntity = saveTransaction(transactionRequest, wallet, TransactionStatus.COMPLETED, TransactionType.FUND);

            saveToTransactionOutBox(wallet, transactionEntity, FundWalletTopic);

            return wallet;
        } catch (RuntimeException exception) {
            TransactionEntity transactionEntity = saveTransaction(transactionRequest, wallet, TransactionStatus.FAILED, TransactionType.FUND);

            saveToTransactionOutBox(wallet, transactionEntity, FundWalletTopic);

            throw new RuntimeException(exception.getMessage());
        }
    }

    @Transactional
    @Override
    public Wallet transferFunds(TransactionRequest transactionRequest, Long walletId) {
        Wallet senderWallet = walletRepository.findById(walletId).orElseThrow(() ->
                new NotFoundException("Wallet not found"));

        Wallet receiverWallet = walletRepository.findById(transactionRequest.walletId()).orElseThrow(() ->
                new NotFoundException("Wallet not found"));
        try {
            if (transactionRequest.amount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BadRequestException("Cannot transfer less than 1");
            }
            if (senderWallet.getBalance().compareTo(transactionRequest.amount()) < 0) {
                TransactionEntity transactionEntity = saveTransaction(transactionRequest, receiverWallet,
                        TransactionStatus.FAILED, TransactionType.TRANSFER_OUT);

                saveToTransactionOutBox(receiverWallet, transactionEntity, TransferWalletTopic);

                throw new BadRequestException("Insufficient balance");
            }

            senderWallet.setBalance(senderWallet.getBalance().subtract(transactionRequest.amount()));
            senderWallet.setUpdatedAt(LocalDateTime.now());

            receiverWallet.setBalance(receiverWallet.getBalance().add(transactionRequest.amount()));
            receiverWallet.setUpdatedAt(LocalDateTime.now());


            TransactionEntity debitTransaction = saveTransaction(transactionRequest, senderWallet,
                    TransactionStatus.COMPLETED, TransactionType.TRANSFER_OUT);

            saveToTransactionOutBox(senderWallet, debitTransaction, TransferWalletTopic);

            TransactionEntity creditTransaction = saveTransaction(transactionRequest, receiverWallet,
                    TransactionStatus.COMPLETED, TransactionType.TRANSFER_IN);

            saveToTransactionOutBox(receiverWallet, creditTransaction, TransferWalletTopic);

            return senderWallet;
        } catch (Exception e) {
            saveTransaction(transactionRequest, receiverWallet,
                    TransactionStatus.FAILED, TransactionType.TRANSFER_OUT);

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Wallet getWallet(Long walletId) {
        return walletRepository.findById(walletId).orElseThrow(() ->
                new NotFoundException("Wallet not found")
        );
    }

    @Override
    public List<Wallet> getUserWallets(Long userId) {
        return walletRepository.findAllByUserId(userId);
    }

    private TransactionEntity saveTransaction(
            TransactionRequest transactionRequest,
            Wallet wallet,
            TransactionStatus status,
            TransactionType transactionType
    ) {
        return transactionRepository.save(
                mappers.toTransaction(transactionRequest, wallet, status, transactionType));
    }

    private void saveToTransactionOutBox(
            Wallet wallet,
            TransactionEntity transactionEntity,
            String topic
    ) {
        TransactionOutBoxEntity entity = mappers.toTransactionOutboxEntity(wallet, transactionEntity, topic);

        transactionOutBoxRepository.save(entity);
    }
}
