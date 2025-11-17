package com.ditial_wallet.historyservice.kafka;

//import com.digital_wallet.avroschema.avro.TransactionAvcEvent;

import com.digital_wallet.avroschema.avro.TransactionAvcEvent;
import com.ditial_wallet.historyservice.entity.TransactionHistoryEntity;
import com.ditial_wallet.historyservice.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionEventConsumers {
    private static final String FundWalletConsumerGroup = "FUND_WALLET_CONSUMER_GROUP";
    public static final String FundWalletTopic = "FUND_WALLET_EVENT";
    public static final String TransferWalletTopic = "TRANSFER_WALLET_EVENT";
    private static final String TransferWalletConsumerGroup = "TRANSFER_WALLET_CONSUMER_GROUP";
    private final HistoryRepository repository;

    @KafkaListener(topics = FundWalletTopic, groupId = FundWalletConsumerGroup)
    public void listenToFundingEvents(ConsumerRecord<String, TransactionAvcEvent> consumerRecord) {
        String key = consumerRecord.key();
        TransactionAvcEvent event = consumerRecord.value();

        log.info("---------------->key: {}, fund event: {}", key, consumerRecord.value());
        saveToDb(event);
    }

    @KafkaListener(topics = TransferWalletTopic, groupId = TransferWalletConsumerGroup)
    public void listen(TransactionAvcEvent event) {
        log.info("---------------->transfer event: {}", event);
        saveToDb(event);
    }

    private void saveToDb(TransactionAvcEvent event) {
        TransactionHistoryEntity transactionEntity = TransactionHistoryEntity.builder()
                .walletId(event.getWalletId())
                .userId(event.getUserId())
                .amount(event.getAmount())
                .eventType(event.getTransactionType())
                .transactionId(event.getTransactionId())
                .createdAt(LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(event.getTimestamp()),
                        ZoneOffset.UTC
                ))
                .build();

        repository.save(transactionEntity);
    }
}
