package com.paylink.exchange.infrastructure.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisCacheConfig {

	@Bean
	RedisCacheConfiguration cacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofHours(24)) //Seria cada hora lo optimo pero el plan gratis de la API actualiza cada 24
				.disableCachingNullValues()
				.serializeKeysWith(
						RedisSerializationContext.SerializationPair.fromSerializer(
								new StringRedisSerializer() //Serializa las claves de #currency y #targetCurrency como string
								)
				
				)
				.serializeValuesWith(
						RedisSerializationContext.SerializationPair.fromSerializer(
								new GenericJackson2JsonRedisSerializer() //Serializa el contenido como JSON
								)
				);
	}

}
