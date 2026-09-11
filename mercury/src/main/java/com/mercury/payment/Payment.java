package com.mercury.payment;

import com.mercury.order.Order;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private LocalDateTime paymentDateAndTime;
    private String paymentType;
    private BigDecimal paymentAmount;

    public Payment() {}

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public Order getOrder() {
        return order;
    }
    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentDateAndTime(LocalDateTime paymentDateAndTime) {
        this.paymentDateAndTime = paymentDateAndTime;
    }
    public LocalDateTime getPaymentDateAndTime() {
        return paymentDateAndTime;
    }
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    public String getPaymentType() {
        return paymentType;
    }
    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }
}
