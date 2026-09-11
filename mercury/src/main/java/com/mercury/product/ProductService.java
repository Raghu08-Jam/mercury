package com.mercury.product;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest request) {
        Product product = ProductMapper.toEntity(request);
        return ProductMapper.toResponse(productRepository.save(product));
    }

    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toResponse)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with the id: " + id));
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Transactional(rollbackOn = Exception.class)
    public void reduceStocks(Long productId, Long quantity) {
        Optional<Product> optionalProduct = productRepository.findWithLockById(productId);
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException("Product not found with the id: " + productId);
        }

        Product product = optionalProduct.get();

        if (product.getStockQuantity() < quantity) {
            throw new InsufficientStockException("Insufficient stock for product with id: " + productId);
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with the id: " + id));
    }
}
