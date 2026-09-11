package com.mercury.order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders/place")
    public ResponseEntity<OrderResponse> placeOrder(@AuthenticationPrincipal Long customerId) {
        OrderResponse orderResponse = orderService.placeOrder(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomerId(@AuthenticationPrincipal Long customerId) {
        List<OrderResponse> orderResponses = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orderResponses);
    }

}
