package com.mercury.order;

import java.math.BigDecimal;

public class OrderItemResponse {

    private Long id;
    private Long productId;
    private String productName;
    private Long quantity;
    private BigDecimal priceAtPurchase;

    public OrderItemResponse() {
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Long getProductId() {
        return productId;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductName() {
        return productName;
    }
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    public Long getQuantity() {
        return quantity;
    }
    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }
    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }
}
