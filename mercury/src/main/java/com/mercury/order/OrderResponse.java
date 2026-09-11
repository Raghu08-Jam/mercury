package com.mercury.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private Long customerId;
    private String customerName;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private List<OrderItemResponse> items;

    public OrderResponse() {
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }
    public List<OrderItemResponse> getItems() {
        return items;
    }
}
