package com.mercury.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product description is required")
    private String description;

    @Positive(message = "Price must be a positive value, it cannot be a negative value or zero")
    private BigDecimal price;

    @NotBlank(message = "Product category is required")
    private String category;

    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock quantity must be a either positive value or zero, it cannot be a negative value")
    private Long stockQuantity;

    public ProductRequest() {}

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public Long getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

}
