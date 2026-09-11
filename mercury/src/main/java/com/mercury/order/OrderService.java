package com.mercury.order;

import com.mercury.cart.Cart;
import com.mercury.cart.CartItem;
import com.mercury.cart.CartService;
import com.mercury.customer.Customer;
import com.mercury.customer.CustomerService;
import com.mercury.product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductService productService;
    private final CustomerService customerService;


    public OrderService(OrderRepository orderRepository,
                        CartService cartService,
                        ProductService productService,
                        CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.customerService = customerService;
    }

    @Transactional(rollbackOn = Exception.class)
    public OrderResponse placeOrder(Long customerId) {
        Cart cart = cartService.getCartEntity(customerId);
        if (cart.getCartItems().isEmpty()) {
            throw new CartEmptyException("Cart is empty. Cannot place order.");
        }
        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getProduct().getPrice());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
            productService.reduceStocks(cartItem.getProduct().getId(), orderItem.getQuantity());
        }
        order.setTotalPrice(order.getOrderItems()
                .stream()
                .map(item -> item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        orderRepository.save(order);
        cartService.clearCart(cart.getCustomer().getId());
        return OrderMapper.toOrderResponse(order);
    }

    public List<OrderResponse> getOrdersByCustomerId(Long customerId) {
        Customer customer = customerService.getCustomer(customerId);
        return orderRepository.findByCustomer(customer)
                .stream()
                .map(OrderMapper::toOrderResponse)
                .toList();
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() ->new OrderNotFoundException("Order not found with id:" + orderId));
    }
}
