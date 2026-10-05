package com.mercury.payment;

import com.mercury.order.Order;
import com.mercury.order.OrderService;
import com.mercury.order.OrderStatus;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public PaymentService(PaymentRepository paymentRepository,
                          OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    @Transactional(rollbackOn = Exception.class)
    public PaymentResponse paymentProcess(Long customerId, Long orderId, PaymentRequest paymentRequest) {
        logger.atInfo().log("payment process for customer with ID: {} for order with ID: {} has been initiated", customerId, orderId);
        Order order = orderService.getOrder(orderId);
        if (!order.getCustomer().getId().equals(customerId)) {
            logger.atWarn().log("Customer with id: {} is not authorized to make payment for the order with id: {} as the order does not belong to the customer", customerId, orderId);
            throw new InvalidOrderException("Customer with id: " + customerId + " is not authorized to make payment for the order with id: " + orderId + " as the order does not belong to the customer");
        }
        if (order.getStatus() == OrderStatus.SUCCESS) {
            logger.atInfo().log("Payment to the order with id: {} has already been done", orderId);
            throw new PaymentAlreadyDoneException("Payment to the order with id: "+ orderId + ", id already done");
        }
        if (!order.getTotalPrice().equals(paymentRequest.getPaidAmount())) {
            logger.atWarn().log("Payment amount provided does not match to the total price of the order. Paid Amount: {}, Total price of order: {}", paymentRequest.getPaidAmount(), order.getTotalPrice());
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
        PaymentResponse response = PaymentMapper.toResponse(paymentRepository.save(payment));
        logger.atInfo().log("Payment for order with ID: {} has been processed successfully with payment ID: {}", orderId, payment.getId());
        return response;
    }
}
