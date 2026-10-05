package com.mercury.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_shouldReturnProductResponse() {
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setDescription("Test Description");
        request.setPrice(BigDecimal.valueOf(100.0));
        request.setCategory("Test Category");
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setCategory("Test Category");
        when(productRepository.save(any(Product.class))).thenReturn(product);
        ProductResponse response = productService.createProduct(request);
        assertEquals(product.getId(), response.getId());
        assertEquals(product.getName(), response.getName());
        assertEquals(product.getDescription(), response.getDescription());
        assertEquals(product.getPrice(), response.getPrice());
        assertEquals(product.getCategory(), response.getCategory());
    }
    @Test
    void getProductById_shouldReturnProductResponse() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setCategory("Test Category");
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ProductResponse response = productService.getProductById(productId);
        assertEquals(product.getId(), response.getId());
        assertEquals(product.getName(), response.getName());
        assertEquals(product.getDescription(), response.getDescription());
        assertEquals(product.getPrice(), response.getPrice());
        assertEquals(product.getCategory(), response.getCategory());
    }
    @Test
    void getProductById_shouldThrowProductNotFoundException() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
    }

    @Test
    void reduceStocks_shouldReduceStockQuantity() {
        Long productId = 1L;
        Long quantityToReduce = 5L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setCategory("Test Category");
        product.setStockQuantity(10L);
        when(productRepository.findWithLockById(productId)).thenReturn(Optional.of(product));
        productService.reduceStocks(productId, quantityToReduce);
        assertEquals(5L, product.getStockQuantity());
    }
    @Test
    void reduceStocks_shouldThrowInsufficientStockException() {
        Long productId = 1L;
        Long quantityToReduce = 15L;
        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setCategory("Test Category");
        product.setStockQuantity(10L);
        when(productRepository.findWithLockById(productId)).thenReturn(Optional.of(product));
        assertThrows(InsufficientStockException.class, () -> productService.reduceStocks(productId, quantityToReduce));
    }
}
