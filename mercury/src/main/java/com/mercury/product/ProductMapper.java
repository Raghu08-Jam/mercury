package com.mercury.product;

import org.springframework.data.domain.Page;

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

    public static PagedResponse<ProductResponse> toPagedResponse(Page<ProductResponse> page) {
        PagedResponse<ProductResponse> pagedResponse = new PagedResponse<>();
        pagedResponse.setContent(page.getContent());
        pagedResponse.setPageNumber(page.getNumber());
        pagedResponse.setPageSize(page.getSize());
        pagedResponse.setTotalPages(page.getTotalPages());
        pagedResponse.setTotalElements(page.getTotalElements());
        pagedResponse.setIsLast(page.isLast());
        pagedResponse.setIsFirst(page.isFirst());
        return pagedResponse;
    }
}
