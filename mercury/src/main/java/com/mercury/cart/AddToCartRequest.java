package com.mercury.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddToCartRequest {
    @NotNull(message = "Product ID cannot be null")
    private Long productId;
    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be greater than zero")
    private Long quantity;

    public AddToCartRequest() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
