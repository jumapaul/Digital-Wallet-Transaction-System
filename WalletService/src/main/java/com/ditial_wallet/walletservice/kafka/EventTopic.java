package com.ditial_wallet.walletservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

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
