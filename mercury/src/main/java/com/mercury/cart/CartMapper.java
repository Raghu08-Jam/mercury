package com.mercury.cart;

import java.math.BigDecimal;

public class CartMapper {
    public static CartResponse toCartResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setId(cart.getId());
        response.setCartItems(cart.getCartItems()
                .stream()
                .map(CartMapper::toCartItemResponse)
                .toList()
        );
        response.setTotalPrice(cart.getCartItems()
                .stream()
                .map(item -> item.getProduct()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        return response;
    }
    public static CartItemResponse toCartItemResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setId(cartItem.getId());
        response.setProductId(cartItem.getProduct().getId());
        response.setProductName(cartItem.getProduct().getName());
        response.setQuantity(cartItem.getQuantity());
        response.setPrice(cartItem.getProduct().getPrice());
        return response;
    }
}
