package com.mercury.order;

import com.mercury.cart.AddToCartRequest;
import com.mercury.cart.CartService;
import com.mercury.customer.CustomerRequest;
import com.mercury.customer.CustomerResponse;
import com.mercury.customer.CustomerService;
import com.mercury.product.ProductRequest;
import com.mercury.product.ProductResponse;
import com.mercury.product.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
public class OrderServiceIntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("mercury_test")
            .withUsername("test")
            .withPassword("test");
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;

    @Test
    void testForPlacingOrderSuccessfully() {
        //creating a customer
        CustomerRequest request = new CustomerRequest();
        request.setName("Test User");
        request.setEmail("TestMail@mail.com");
        request.setPassword("TestPassword");
        request.setPhoneNumber("1234567890");
        request.setAddress("Test Address");
        CustomerResponse customerResponse = customerService.createCustomer(request);

        //creating few products
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Test Product 1");
        productRequest.setDescription("Test Description 1");
        productRequest.setPrice(BigDecimal.valueOf(100.0));
        productRequest.setCategory("Test Category 1");
        productRequest.setStockQuantity(10L);
        ProductResponse productResponse1 = productService.createProduct(productRequest);
        productRequest.setName("Test Product 2");
        productRequest.setDescription("Test Description 2");
        productRequest.setPrice(BigDecimal.valueOf(200.0));
        productRequest.setCategory("Test Category 2");
        productRequest.setStockQuantity(10L);
        ProductResponse productResponse2 = productService.createProduct(productRequest);

        //adding products to cart
        AddToCartRequest addToCartRequest = new AddToCartRequest();
        addToCartRequest.setProductId(productResponse1.getId());
        addToCartRequest.setQuantity(5L);
        cartService.addToCart(customerResponse.getId(), addToCartRequest);
        addToCartRequest.setProductId(productResponse2.getId());
        addToCartRequest.setQuantity(5L);
        cartService.addToCart(customerResponse.getId(), addToCartRequest);

        //placing order
        OrderResponse orderResponse = orderService.placeOrder(customerResponse.getId());

        assertEquals(0, BigDecimal.valueOf(1500.0).compareTo(orderResponse.getTotalPrice()));
        assertEquals(0, orderResponse.getTotalPrice().compareTo(BigDecimal.valueOf(1500.0)));
        assertEquals(5, orderResponse.getItems().getFirst().getQuantity());
        assertEquals(5, orderResponse.getItems().getLast().getQuantity());
        assertEquals("PENDING", orderResponse.getStatus().name());
    }
}
