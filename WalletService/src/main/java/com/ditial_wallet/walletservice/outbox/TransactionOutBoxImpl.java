package com.ditial_wallet.walletservice.outbox;

import com.digital_wallet.avroschema.avro.TransactionAvcEvent;
import com.ditial_wallet.walletservice.kafka.KafkaProducer;
import com.ditial_wallet.walletservice.kafka.TransactionEventWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
public class TransactionOutBoxImpl {
    private final KafkaProducer producer;
    private final TransactionOutBoxRepository outBoxRepository;

    @Scheduled(fixedRate = 10000)
    public void pullAndPublishToKafka() {
        try {
            List<TransactionOutBoxEntity> unprocessedRequests = outBoxRepository.findAllByProcessedFalse();

            unprocessedRequests.forEach(transactionOutBoxEntity -> {
                publishEvent(transactionOutBoxEntity, transactionOutBoxEntity.getTopic());

                transactionOutBoxEntity.setProcessed(true);
                outBoxRepository.save(transactionOutBoxEntity);
            });


        } catch (RuntimeException exception) {
            log.error("------------> {}", exception.getMessage());
        }

    }

    private void publishEvent(
            TransactionOutBoxEntity transaction,
            String topic
    ) {
        log.info("---------------->published: {}", transaction);
        TransactionAvcEvent event = new TransactionAvcEvent(
                transaction.getTransactionType().name(),
                transaction.getStatus().name(),
                transaction.getWalletId(),
                transaction.getUserId(),
                transaction.getAmount(),
                transaction.getId(),
                transaction.getTimestamp().toInstant(ZoneOffset.UTC).toEpochMilli()
        );

        producer.publishMessage(new TransactionEventWrapper(event, transaction.getTransactionType(), topic));
    }
}
