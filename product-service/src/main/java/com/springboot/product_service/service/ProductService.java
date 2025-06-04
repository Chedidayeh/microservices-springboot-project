package com.springboot.product_service.service;

// Import statements: Bring in necessary classes and annotations
import com.springboot.product_service.dto.ProductRequest;  // DTO class for product request data
import com.springboot.product_service.dto.ProductResponse;
import com.springboot.product_service.model.Product;      // Product entity class
import com.springboot.product_service.repository.ProductRepository;  // Repository for database operations
import lombok.RequiredArgsConstructor;  // Generates a constructor with required fields
import lombok.extern.slf4j.Slf4j;       // Enables logging with SLF4J
import org.springframework.stereotype.Service;  // Marks this class as a Spring service

import java.util.List;

// @Service: Indicates this is a Spring service component, managed by the Spring container
@Service
// @RequiredArgsConstructor: Generates a constructor for final fields (e.g., productRepository)
@RequiredArgsConstructor
// @Slf4j: Enables logging using SLF4J (log.info, log.error, etc.)
@Slf4j
public class ProductService {

    // Final field: Injects the ProductRepository bean, used for database operations
    private final ProductRepository productRepository;

    // Method: Creates a new product based on the provided ProductRequest
    public void createProduct(ProductRequest productRequest) {
        // Builds a Product object using the Builder pattern from the request data
        Product product = Product.builder()
                .name(productRequest.getName())        // Sets the product name
                .description(productRequest.getDescription())  // Sets the product description
                .price(productRequest.getPrice())      // Sets the product price
                .build();

        // Saves the product to the MongoDB database
        productRepository.save(product);

        // Logs an info message with the created product's ID
        log.info("Product {} created", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}