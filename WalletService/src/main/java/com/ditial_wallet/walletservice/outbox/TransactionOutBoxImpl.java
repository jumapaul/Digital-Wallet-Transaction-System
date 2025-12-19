package com.ditial_wallet.walletservice.outbox;

import com.digital_wallet.avroschema.avro.TransactionAvcEvent;
import com.ditial_wallet.walletservice.kafka.KafkaProducer;
import com.ditial_wallet.walletservice.kafka.TransactionEventWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
public class TransactionOutBoxImpl {
    private final KafkaProducer producer;
    private final TransactionOutBoxRepository outBoxRepository;

    /*
    Scheduler that consume events from outbox table and publish to kafka.
     */
    @Scheduled(fixedRate = 10000)
    public void pullAndPublishToKafka() {
        List<TransactionOutBoxEntity> unprocessedRequests = outBoxRepository.findAllByProcessedFalse();

        unprocessedRequests.forEach(transactionOutBoxEntity -> {
            publishEvent(transactionOutBoxEntity, transactionOutBoxEntity.getTopic()).whenComplete((result, ex) -> {
                //if we receive acknowledgement and no error then set processed state to true.
                if (ex == null) {
                    log.info("Kafka ack received for event {}", transactionOutBoxEntity);
                    transactionOutBoxEntity.setProcessed(true);
                    outBoxRepository.save(transactionOutBoxEntity);
                } else {
                    log.error("Kafka send failed for event {}", transactionOutBoxEntity);
                }
            });
        });

    }

    private CompletableFuture<SendResult<String, TransactionAvcEvent>> publishEvent(
            TransactionOutBoxEntity transaction,
            String topic
    ) {
        TransactionAvcEvent event = new TransactionAvcEvent(
                transaction.getTransactionType().name(),
                transaction.getStatus().name(),
                transaction.getWalletId(),
                transaction.getUserId(),
                transaction.getAmount(),
                transaction.getId(),
                transaction.getTimestamp().toInstant(ZoneOffset.UTC).toEpochMilli()
        );

        return producer.publishMessage(new TransactionEventWrapper(event, transaction.getTransactionType(), topic));
    }
}
