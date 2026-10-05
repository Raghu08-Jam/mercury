package com.mercury.exception;

import com.mercury.auth.InvalidCredentialsException;
import com.mercury.cart.CartNotFoundException;
import com.mercury.cart.InvalidDeltaException;
import com.mercury.customer.CustomerNotFoundException;
import com.mercury.order.CartEmptyException;
import com.mercury.order.OrderNotFoundException;
import com.mercury.payment.InvalidOrderException;
import com.mercury.payment.PaymentAlreadyDoneException;
import com.mercury.payment.PaymentAmountMismatchException;
import com.mercury.product.InsufficientStockException;
import com.mercury.product.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<List<String>>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        ErrorResponse<List<String>> response = new ErrorResponse<>(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), errors, request.getRequestURI(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleProductNotFoundException(ProductNotFoundException ex, HttpServletRequest request) {
        ErrorResponse<String> response = new ErrorResponse<>(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse<String>> handleInsufficientStockException(InsufficientStockException ex, HttpServletRequest request) {
        ErrorResponse<String> response = new ErrorResponse<>(LocalDateTime.now(), HttpStatus.CONFLICT.value(), ex.getMessage(), request.getRequestURI(), HttpStatus.CONFLICT.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleCustomerNotFoundException(CustomerNotFoundException ex, HttpServletRequest request) {
        ErrorResponse<String> response = new ErrorResponse<>(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(CartEmptyException.class)
    public ResponseEntity<ErrorResponse<String>> handleCartEmptyException(CartEmptyException ex, HttpServletRequest request) {
        ErrorResponse<String> response = new ErrorResponse<>(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleOrderNotFoundException(OrderNotFoundException ex, HttpServletRequest request) {
        ErrorResponse<String> response = new ErrorResponse<>(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(PaymentAlreadyDoneException.class)
    public ResponseEntity<ErrorResponse<String>> handlePaymentAlreadyDoneException(PaymentAlreadyDoneException ex, HttpServletRequest request) {
        ErrorResponse<String> response = new ErrorResponse<>(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(PaymentAmountMismatchException.class)
    public ResponseEntity<ErrorResponse<String>> handlePaymentAmountMismatchException(PaymentAmountMismatchException ex, HttpServletRequest request) {
        ErrorResponse<String> response = new ErrorResponse<>(LocalDateTime.now(), HttpStatus.CONFLICT.value(), ex.getMessage(), request.getRequestURI(), HttpStatus.CONFLICT.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse<String>> handleInvalidCredentialsException(InvalidCredentialsException ex, HttpServletRequest request) {
        ErrorResponse<String> response = new ErrorResponse<>(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), request.getRequestURI(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<ErrorResponse<String>> handleInvalidOrderException(InvalidOrderException ex, HttpServletRequest request) {
        ErrorResponse<String> response = new ErrorResponse<>(LocalDateTime.now(), HttpStatus.FORBIDDEN.value(), ex.getMessage(), request.getRequestURI(), HttpStatus.FORBIDDEN.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleCartNotFoundException(CartNotFoundException ex, HttpServletRequest request) {
        ErrorResponse<String> response = new ErrorResponse<>(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidDeltaException.class)
    public ResponseEntity<ErrorResponse<String>> handleInvalidDeltaException(InvalidDeltaException ex, HttpServletRequest request) {
        ErrorResponse<String> response = new ErrorResponse<>(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<String>> handleGenericException(Exception ex, HttpServletRequest request) {
        logger.atError().setCause(ex).log("An unexpected error occurred.");
        ErrorResponse<String> response = new ErrorResponse<>(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred. Please try again later.", request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }


}
