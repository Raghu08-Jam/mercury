package com.mercury.cart;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/cart/add")
    public ResponseEntity<CartResponse> addToCart(@AuthenticationPrincipal Long customerId,
                                                  @Valid @RequestBody AddToCartRequest addToCartRequest) {
        return ResponseEntity.ok(cartService.addToCart(customerId, addToCartRequest));
    }

    @GetMapping("/cart")
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal Long customerId) {
        return ResponseEntity.ok(cartService.getCart(customerId));
    }

    @DeleteMapping("/cart/remove/{productId}")
    public ResponseEntity<CartResponse> removeItemFromCart(@AuthenticationPrincipal Long customerId,
                                                           @PathVariable Long productId) {
        return ResponseEntity.ok(cartService.removeItemFromCart(customerId, productId));
    }

    @DeleteMapping("/cart/clear")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal Long customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/cart/update")
    public ResponseEntity<CartResponse> updateCartItem(@AuthenticationPrincipal Long customerId,
                                                       @Valid @RequestBody CartItemsUpdateRequest updateRequest) {
        return ResponseEntity.ok(cartService.updateItemInCart(customerId, updateRequest));
    }
}