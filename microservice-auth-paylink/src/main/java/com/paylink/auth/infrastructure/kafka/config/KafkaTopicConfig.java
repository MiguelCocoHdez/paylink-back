package com.paylink.auth.infrastructure.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

	@Bean
	NewTopic userRegisteredTopic() {
		return TopicBuilder.name("user-registered")
				.partitions(1)
				.replicas(1)
				.build();
	}
}
