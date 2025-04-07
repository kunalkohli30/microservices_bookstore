package com.microservices.catalog_service.domain;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }

    public static ProductNotFoundException forCode(String code) {
        return new ProductNotFoundException(String.format("Product not found with code: %s", code));
    }
}
