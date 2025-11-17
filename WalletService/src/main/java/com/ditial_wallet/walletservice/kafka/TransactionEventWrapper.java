package com.ditial_wallet.walletservice.kafka;

import com.digital_wallet.avroschema.avro.TransactionAvcEvent;
import com.ditial_wallet.walletservice.dtos.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionEventWrapper {
    private final TransactionAvcEvent event;
    private final TransactionType eventType;
    private final String topic;
}
