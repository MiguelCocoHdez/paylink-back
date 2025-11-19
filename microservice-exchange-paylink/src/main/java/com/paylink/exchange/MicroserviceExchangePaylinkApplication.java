package com.paylink.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MicroserviceExchangePaylinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceExchangePaylinkApplication.class, args);
	}

}
