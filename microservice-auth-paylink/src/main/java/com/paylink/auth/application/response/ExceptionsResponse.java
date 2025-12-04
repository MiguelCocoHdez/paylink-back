package com.paylink.auth.application.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Builder
public class ExceptionsResponse {

	private LocalDateTime timestamp;
	private HttpStatus status;
	private String message;
}
