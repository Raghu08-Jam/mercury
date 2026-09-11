package com.mercury.payment;

import com.mercury.order.Order;
import com.mercury.order.OrderService;
import com.mercury.order.OrderStatus;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public PaymentService(PaymentRepository paymentRepository,
                          OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    @Transactional(rollbackOn = Exception.class)
    public PaymentResponse paymentProcess(Long customerId, Long orderId, PaymentRequest paymentRequest) {
        Order order = orderService.getOrder(orderId);
        if (!order.getCustomer().getId().equals(customerId)) {
            throw new InvalidOrderException("Customer with id: " + customerId + " is not authorized to make payment for the order with id: " + orderId + " as the order does not belong to the customer");
        }
        if (order.getStatus() == OrderStatus.SUCCESS) {
            throw new PaymentAlreadyDoneException("Payment to the order with id: "+ orderId + ", id already done");
        }
        if (!order.getTotalPrice().equals(paymentRequest.getPaidAmount())) {
            throw new PaymentAmountMismatchException("Payment amount provided does not match to the total price of the order. Paid Amount: " + paymentRequest.getPaidAmount() + ", Total price of order: " + order.getTotalPrice());
        }
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentAmount(paymentRequest.getPaidAmount());
        payment.setPaymentDateAndTime(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setPaymentType(paymentRequest.getPaymentMethod());
        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            order.setStatus(OrderStatus.SUCCESS);
        }
        return PaymentMapper.toResponse(paymentRepository.save(payment));
    }
}
