package com.mercury.cart;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CartItemsUpdateRequest {
    @NotNull(message = "Product ID cannot be null")
    private Long productId;
    @Min(value = -1, message = "Delta must be -1, 0, or 1")
    @Max(value = 1, message = "Delta must be -1, 0, or 1")
    private Long delta;

    public CartItemsUpdateRequest() {}

    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public Long getProductId() {
        return productId;
    }
    public void setDelta(Long delta) {
        this.delta = delta;
    }
    public Long getDelta() {
        return delta;
    }
}
