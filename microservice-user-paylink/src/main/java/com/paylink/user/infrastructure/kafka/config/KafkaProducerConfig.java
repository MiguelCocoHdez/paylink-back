package com.paylink.user.infrastructure.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.paylink.kafka.events.TransactionCreatedEvent;

@Configuration
@EnableKafka
public class KafkaProducerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	Map<String, Object> producerConfig() {
		HashMap<String, Object> props = new HashMap<>();
		
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		return props;
	}
	
	@Bean
	ProducerFactory<String, TransactionCreatedEvent> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfig());
	}
	
	@Bean
	KafkaTemplate<String, TransactionCreatedEvent> kafkaTemplate(ProducerFactory<String, TransactionCreatedEvent> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}
	
}
