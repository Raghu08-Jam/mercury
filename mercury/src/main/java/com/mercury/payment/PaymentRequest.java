package com.mercury.payment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class PaymentRequest {

    @NotNull(message = "payment must be specified, it cannot be blank")
    private String paymentMethod;
    @NotNull(message = "Payment amount cannot be blank")
    @Positive(message = "Payment amount cannot be ZERO(0) or negative amount")
    private BigDecimal paidAmount;

    public PaymentRequest() {}

    public void  setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public void  setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public BigDecimal getPaidAmount() {
        return paidAmount;
    }
}
