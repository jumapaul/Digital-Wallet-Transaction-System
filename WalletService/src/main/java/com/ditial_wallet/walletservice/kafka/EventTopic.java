package com.ditial_wallet.walletservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka topics to publish our events
 * All fund wallet transactions are published on fund wallet event.
 * All wallet transfers are published on transfer wallet event.
 */

@Configuration
public class EventTopic {

    @Bean
    public NewTopic fundWalletEvent() {
        return TopicBuilder.name("FUND_WALLET_EVENT")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic transferToWalletEvent() {
        return TopicBuilder.name("TRANSFER_WALLET_EVENT")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
