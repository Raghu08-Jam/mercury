package com.mercury.payment;

public class PaymentAmountMismatchException extends RuntimeException {
    public PaymentAmountMismatchException(String message) {
        super(message);
    }
}
