package com.paylink.transation.infrastructure.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	@Bean
	NewTopic transactionSuccessTopic() {
		return TopicBuilder.name("transaction-success")
				.partitions(1)
				.replicas(1)
				.build();
	}
	
	@Bean
	NewTopic transactionFailedTopic() {
		return TopicBuilder.name("transaction-failed")
				.partitions(1)
				.replicas(1)
				.build();
	}
	
	@Bean
	NewTopic differentExchangeTransactionTopic() {
		return TopicBuilder.name("different-exchange-transaction")
				.partitions(1)
				.replicas(1)
				.build();
	}
}
