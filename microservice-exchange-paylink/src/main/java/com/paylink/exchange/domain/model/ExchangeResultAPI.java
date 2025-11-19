package com.paylink.exchange.domain.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor

@JsonIgnoreProperties(ignoreUnknown = true)

public class ExchangeResultAPI {

	private String result;
	private String base_code;
	private String target_code;
	private BigDecimal conversion_rate;
}
