package com.mercury.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<PagedResponse<ProductResponse>> getAllProducts(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequest));
    }

    @PatchMapping("/products/{id}/reduce-stocks")
    public ResponseEntity<ProductResponse> reduceStock(@PathVariable Long id, @Positive @RequestParam Long quantity) {
        productService.reduceStocks(id, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/products/update-stock")
    public ResponseEntity<ProductResponse> updateProductStock(@Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProductStock(productUpdateRequest));
    }
}
