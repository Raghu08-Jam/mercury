package com.mercury.payment;

import com.mercury.order.OrderMapper;

public class PaymentMapper {
    public static PaymentResponse toResponse(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.setId(payment.getId());
        response.setPaymentType(payment.getPaymentType());
        response.setPaymentAmount(payment.getPaymentAmount());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setPaymentDateAndTime(payment.getPaymentDateAndTime());
        response.setOrder(OrderMapper.toOrderResponse(payment.getOrder()));
        return response;
    }
}
