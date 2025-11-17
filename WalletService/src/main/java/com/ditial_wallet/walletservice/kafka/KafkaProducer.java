package com.ditial_wallet.walletservice.kafka;

import com.digital_wallet.avroschema.avro.TransactionAvcEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Component
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, TransactionAvcEvent> kafkaTemplate;

    public void publishMessage(TransactionEventWrapper avcEvent) {
        kafkaTemplate.send(avcEvent.getTopic(), avcEvent.getEvent());
    }
}
