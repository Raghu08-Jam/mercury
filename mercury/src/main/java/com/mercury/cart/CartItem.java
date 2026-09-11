package com.mercury.cart;

import com.mercury.product.Product;
import jakarta.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Long quantity;

    public CartItem() {}

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public Cart getCart() {
        return cart;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Product getProduct() {
        return product;
    }
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
    public Long getQuantity() {
        return quantity;
    }

}
