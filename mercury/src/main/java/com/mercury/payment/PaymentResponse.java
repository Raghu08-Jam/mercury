package com.mercury.payment;

import com.mercury.order.OrderResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponse {
    private Long id;
    private OrderResponse order;
    private PaymentStatus paymentStatus;
    private BigDecimal paymentAmount;
    private LocalDateTime paymentDateAndTime;
    private String paymentType;

    public PaymentResponse() {}

    public void setId(Long id) {
        this.id = id;
    }
    public void setOrder(OrderResponse order) {
        this.order = order;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    public void setPaymentDateAndTime(LocalDateTime paymentDateAndTime) {
        this.paymentDateAndTime = paymentDateAndTime;
    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    public Long getId() {
        return id;
    }
    public OrderResponse getOrder() {
        return order;
    }
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }
    public LocalDateTime getPaymentDateAndTime() {
        return paymentDateAndTime;
    }
    public String getPaymentType() {
        return paymentType;
    }
}
