package com.mercury.product;

import java.math.BigDecimal;

public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String category;
    private Long stockQuantity;
    private BigDecimal price;

    public ProductResponse() {}
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
