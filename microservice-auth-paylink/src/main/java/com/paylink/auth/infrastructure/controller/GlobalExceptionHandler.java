package com.paylink.auth.infrastructure.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.paylink.auth.application.response.ExceptionsResponse;
import com.paylink.auth.domain.exception.InvalidCredentialsException;
import com.paylink.auth.domain.exception.InvalidEmailOrUsernameException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InvalidCredentialsException.class) //401 login invalido
	public ResponseEntity<ExceptionsResponse> handleInvalidLogin(InvalidCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
				ExceptionsResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.UNAUTHORIZED)
				.message(ex.getMessage())
				.build());
	}
	
	@ExceptionHandler(InvalidEmailOrUsernameException.class) //409 conflict
	public ResponseEntity<ExceptionsResponse> handleInvalidRegister(InvalidCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(
				ExceptionsResponse.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.CONFLICT)
				.message(ex.getMessage())
				.build());
	}
}
