package com.mercury.product;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse createProduct(ProductRequest request) {
        Product product = ProductMapper.toEntity(request);
        ProductResponse response = ProductMapper.toResponse(productRepository.save(product));
        logger.atInfo().log("Product created with ID: {} [an admin only feature]", response.getId());
        return response;
    }

    @Cacheable(value = "product", key = "#id")
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toResponse)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with the id: " + id));
    }

    @Cacheable(value = "products", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.getSort().toString()")
    public PagedResponse<ProductResponse> getAllProducts(Pageable pageable) {
        Page<ProductResponse> page = productRepository.findAll(pageable).map(ProductMapper::toResponse);
        return ProductMapper.toPagedResponse(page);
    }

    @Caching(evict = {
            @CacheEvict(value = "product", key = "#productId"),
            @CacheEvict(value = "products", allEntries = true)
    })
    @Transactional(rollbackOn = Exception.class)
    public void reduceStocks(Long productId, Long quantity) {
        Optional<Product> optionalProduct = productRepository.findWithLockById(productId);
        if (optionalProduct.isEmpty()) {
            logger.atWarn().log("Attempted to reduce stock for non-existent product with id: {}", productId);
            throw new ProductNotFoundException("Product not found with the id: " + productId);
        }

        Product product = optionalProduct.get();

        if (product.getStockQuantity() < quantity) {
            logger.atWarn().log("Insufficient stock for product with id: {}, available: {}, attempted reduce: {}", productId, product.getStockQuantity(), quantity);
            throw new InsufficientStockException("Insufficient stock for product with id: " + productId);
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
        logger.atInfo().log("Reduced stock for product with id: {}, new stock quantity: {}", productId, product.getStockQuantity());
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with the id: " + id));
    }

    public ProductResponse updateProductPrice(Long productId, BigDecimal newPrice) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with the id: " + productId));
        product.setPrice(newPrice);
        ProductResponse response = ProductMapper.toResponse(productRepository.save(product));
        logger.atInfo().log("Updated price for product with id: {}, new price: {} [an admin only feature]", productId, newPrice);
        return response;
    }

    public ProductResponse updateProductStock(ProductUpdateRequest request) {
        Long productId = request.getProductId();
        Long newStockQuantity = request.getNewStockQuantity();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with the id: " + productId));
        product.setStockQuantity(newStockQuantity);
        ProductResponse response = ProductMapper.toResponse(productRepository.save(product));
        logger.atInfo().log("Updated stock quantity for product with id: {}, new stock quantity: {} [an admin only feature]", productId, newStockQuantity);
        return response;
    }
}
