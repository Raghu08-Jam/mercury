package com.mercury.payment;

public class PaymentAlreadyDoneException extends RuntimeException {
    public PaymentAlreadyDoneException(String message) {
        super(message);
    }
}
