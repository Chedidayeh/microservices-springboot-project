package com.springboot.product_service.controller;

// Import statements: Bring in necessary classes and annotations
import com.springboot.product_service.dto.ProductRequest;  // DTO class for product request data
import com.springboot.product_service.dto.ProductResponse;
import com.springboot.product_service.model.Product;      // Product entity class (though not used here)
import com.springboot.product_service.service.ProductService;  // Service layer for product operations
import lombok.RequiredArgsConstructor;  // Generates a constructor for required fields
import org.springframework.http.HttpStatus;  // Provides HTTP status codes
import org.springframework.web.bind.annotation.*;  // Annotations for RESTful endpoints

import java.util.List;

// @RestController: Marks this class as a REST controller, handling HTTP requests and returning JSON responses
@RestController
// @RequestMapping: Defines the base URL path for all endpoints in this controller (/api/product)
@RequestMapping("/api/product")
// @RequiredArgsConstructor: Generates a constructor for final fields (e.g., productService)
@RequiredArgsConstructor
public class ProductController {

    // Immutability: In this case, making productRepository and productService final ensures they are not accidentally reassigned after Spring injects them, reducing the risk of bugs.
    // Dependency Injection: Using final with @RequiredArgsConstructor enforces constructor injection, which is a best practice in Spring. It makes dependencies explicit and ensures they are provided when the object is created.
    // Thread Safety: Immutability (via final) helps in concurrent environments by preventing unexpected changes to the field.

    // Final field: Injects the ProductService bean, used to handle business logic
    private final ProductService productService;

    // @PostMapping: Maps HTTP POST requests to this method at /api/product
    @PostMapping
    // @ResponseStatus: Sets the HTTP response status to 201 Created upon successful execution
    @ResponseStatus(HttpStatus.CREATED)
    // Method: Handles product creation requests
    // @RequestBody: Binds the HTTP request body (JSON) to the ProductRequest object
    public void createProduct(@RequestBody ProductRequest productRequest) {
        // Delegates the creation logic to the ProductService
        productService.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }
}