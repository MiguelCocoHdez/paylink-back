package com.paylink.exchange.infrastructure.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	@Bean
	NewTopic differentExchangeTransactionTopic() {
		return TopicBuilder.name("exchange-success")
				.partitions(1)
				.replicas(1)
				.build();
	}
}
