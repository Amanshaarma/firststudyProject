package com.tms.Main.Expection;

import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.tms.Main.response.ApiResponsePattern;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 404 - Not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponsePattern<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponsePattern.failure(ex.getMessage()));
    }

    @ExceptionHandler(CompanyNofFound.class)
    public ResponseEntity<ApiResponsePattern<Object>> handleCompanyNotFound(CompanyNofFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponsePattern.failure(ex.getMessage()));
    }

    // 409 - Duplicate resource
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiResponsePattern<Object>> handleDuplicateResourceException(DuplicateResourceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponsePattern.failure(ex.getMessage()));
    }

    // 409 - DB-level conflict (FK violation on delete, unique constraint on insert, etc.)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponsePattern<Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.warn("Data integrity violation", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponsePattern.failure(
                        "Operation conflicts with existing data. This record may be referenced by other resources."));
    }

    // 422 - Business/semantic validation failure (syntactically valid request, invalid per business rules)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponsePattern<Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApiResponsePattern.failure(ex.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponsePattern<Object>> handleValidation(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiResponsePattern.failure("Validation failed: " + ex.getMessage()));
    }

    // 422 - @Valid bean validation failures (e.g. required field empty)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponsePattern<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiResponsePattern.failure("Validation failed", errors));
    }

    // 400 - Malformed JSON / unreadable request body
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponsePattern<Object>> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponsePattern.failure("Malformed request body" + ex.getMessage()));
    }

    // 400 - Wrong type for a path variable / query param (e.g. /vehicles/abc where id must be Long)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponsePattern<Object>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiResponsePattern.failure("Invalid value for parameter: " + ex.getName()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public  ResponseEntity<ApiResponsePattern<Object>> handelNoResourceFoundException(NoResourceFoundException ex)
    {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ApiResponsePattern.failure("invalid business logic " + ex.getMessage()));
    }
    // 405 - Wrong HTTP method
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponsePattern<Object>> handleMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiResponsePattern.failure("HTTP method not supported: " + ex.getMethod()));
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponsePattern<?>> handleNoHandlerFound(
            NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponsePattern.failure(
                        "API endpoint not found: " + ex.getRequestURL(),
                        null
                ));
    }

    // 500 - Any unhandled exception. Never leak internal exception details to the client.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponsePattern<Object>> handleGlobalException(Exception ex) {
        log.error("Unhandled exception", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponsePattern.failure("An unexpected error occurred. Please try again later."));
    }
}