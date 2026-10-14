package com.mercury.product;

import jakarta.validation.constraints.NotBlank;

public class ProductUpdateRequest {

    private Long productId;
    private Long newStockQuantity;

    public ProductUpdateRequest() {}

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getNewStockQuantity() {
        return newStockQuantity;
    }

    public void setNewStockQuantity(Long newStockQuantity) {
        this.newStockQuantity = newStockQuantity;
    }
}
