package com.mercury.payment;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    private final PaymentService paymentService;
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments/{orderId}/pay")
    public ResponseEntity<PaymentResponse> paymentProcess(@AuthenticationPrincipal Long customerId, @PathVariable("orderId") Long orderId,
                                                          @Valid @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.paymentProcess(customerId, orderId, paymentRequest));
    }

}
