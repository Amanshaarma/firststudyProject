package com.study.Main.Expection;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.study.Main.response.ApiResponsePattern;

//exception/GlobalExceptionHandler.java
@RestControllerAdvice
public class GlobalExceptionHandler {

	// 404 - Not Found
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponsePattern<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponsePattern.failure(ex.getMessage()));
	}

	// 409 - Duplicate
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ApiResponsePattern<Object>> handleDuplicateResourceException(DuplicateResourceException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponsePattern.failure(ex.getMessage()));
	}

	// 400 - Bad Request
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiResponsePattern<Object>> handleBadRequestException(BadRequestException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponsePattern.failure(ex.getMessage()));
	}

	@ExceptionHandler(CompanyNofFound.class)
	public ResponseEntity<ApiResponsePattern<Object>> handleBCompanyNofFound(CompanyNofFound ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponsePattern.failure(ex.getMessage()));
	}

	// 400 - @Valid validation failures
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponsePattern<Map<String, String>>> handleValidationException(
			MethodArgumentNotValidException ex) {

		Map<String, String> errors = new LinkedHashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ApiResponsePattern.failure("Validation failed", errors));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	    public ResponseEntity<ApiResponsePattern<Object>> handleDataIntegrityViolation(
	            DataIntegrityViolationException ex) {

	        String message = "Unable to delete this ledger because it is associated with existing transactions." + ex.getMessage();

	        return ResponseEntity.badRequest()
	                .body(ApiResponsePattern.failure( message));
	}
	// 400 - Invalid JSON body
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiResponsePattern<Object>> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ApiResponsePattern.failure("Invalid request body format" + ex.getMessage()));
	}

	// 405 - Wrong HTTP method
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponsePattern<Object>> handleMethodNotSupportedException(
			HttpRequestMethodNotSupportedException ex) {
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(ApiResponsePattern.failure("HTTP method not supported: " + ex.getMethod()));
	}

	// 500 - Any unhandled exception
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponsePattern<Object>> handleGlobalException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponsePattern.failure(ex.getMessage()));
	}
}
