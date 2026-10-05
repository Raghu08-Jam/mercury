package com.mercury.cart;

import com.mercury.customer.Customer;
import com.mercury.customer.CustomerService;
import com.mercury.product.Product;
import com.mercury.product.ProductService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       CustomerService customerService,
                       ProductService productService) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Transactional(rollbackOn =  Exception.class)
    public CartResponse addToCart(Long customerId, AddToCartRequest request) {
        Customer customer = customerService.getCustomer(customerId);
        Cart cart = getOrCreateCart(customer);
        Product product = productService.getProduct(request.getProductId());
        Optional<CartItem> cartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (cartItem.isPresent()) {
            CartItem existingCartItem = cartItem.get();
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            cart.getCartItems().add(existingCartItem);
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(request.getQuantity());
            cart.getCartItems().add(newCartItem);
            cartItemRepository.save(newCartItem);
        }
        return CartMapper.toCartResponse(cart);
    }

    @Transactional(rollbackOn =  Exception.class)
    public CartResponse getCart(Long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        Cart cart = getOrCreateCart(customer);
        return CartMapper.toCartResponse(cart);
    }

    @Transactional(rollbackOn =  Exception.class)
    public CartResponse removeItemFromCart(Long customerId, Long productId) {
        Customer customer = customerService.getCustomer(customerId);
        Cart cart = getCartOrThrow(customer);
        Product product = productService.getProduct(productId);
        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (optionalCartItem.isEmpty()) {
            logger.atWarn().log("attempted to remove a item from cart which does not exist in the current cart by customer of id : {}", customerId);
            throw new CartItemNotFoundException("Cart item not found for product with ID: " + productId);
        }
        CartItem cartItem = optionalCartItem.get();
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        logger.atInfo().log("removed item of id : {} from cart for cart id {}", cartItem.getId(), cart.getId());
        return CartMapper.toCartResponse(cart);
    }

    @Transactional(rollbackOn =  Exception.class)
    public CartResponse updateItemInCart(Long customerId, CartItemsUpdateRequest request) {
        if (request.getDelta() == 0) {
            logger.atWarn().log("attempted to update cart item with a delta of zero which does nothing for customer of id : {}", customerId);
            throw new InvalidDeltaException("Delta cannot be zero which does absolutely nothing. Please provide a delta of -1 or 1 to decrease or increase the quantity respectively.");
        }
        Customer customer = customerService.getCustomer(customerId);
        Cart cart = getCartOrThrow(customer);
        Product product = productService.getProduct(request.getProductId());
        Optional<CartItem> optionalCartItem = cartItemRepository.findByCartAndProduct(cart, product);
        if (optionalCartItem.isEmpty()) {
            logger.atWarn().log("attempted to update a cart item which does not exist for customer of id : {}", customerId);
            throw new CartItemNotFoundException("Cart item not found for product with ID: " + request.getProductId());
        }
        CartItem cartItem = optionalCartItem.get();
        if (cartItem.getQuantity() + request.getDelta() <= 0) {
            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
            return CartMapper.toCartResponse(cart);
        }
        cartItem.setQuantity(cartItem.getQuantity() + request.getDelta());
        cartItemRepository.save(cartItem);
        logger.atInfo().log("updated item of id : {} in cart for cart id {}", cartItem.getId(), cart.getId());
        return CartMapper.toCartResponse(cart);
    }

    private Cart getOrCreateCart(Customer customer) {
        return cartRepository.findByCustomer(customer)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomer(customer);
                    return cartRepository.save(newCart);
                });
    }
    public Cart getCartEntity(Long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        return getOrCreateCart(customer);
    }
    public void clearCart(Long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        Cart cart = getCartOrThrow(customer);
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
    private Cart getCartOrThrow(Customer customer) {
        return cartRepository.findByCustomer(customer)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for customer with ID: " + customer.getId()));
    }
}
