package com.mercury.order;

import com.mercury.product.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal priceAtPurchase;
    public OrderItem() {
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    public Long getQuantity() {
        return quantity;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public Order getOrder() {
        return order;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Product getProduct() {
        return product;
    }
    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }
    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }
}
