package com.mercury.product;

public class ProductMapper {
    public static Product toEntity(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setStockQuantity(request.getStockQuantity());
        product.setPrice(request.getPrice());
        return product;
    }

    public static ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setCategory(product.getCategory());
        response.setStockQuantity(product.getStockQuantity());
        response.setPrice(product.getPrice());
        return response;
    }
}
