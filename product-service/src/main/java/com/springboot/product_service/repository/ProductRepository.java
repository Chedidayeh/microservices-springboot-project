package com.springboot.product_service.repository;

// Import statements: Bring in the Product model and MongoRepository for database operations
import com.springboot.product_service.model.Product;  // Refers to the Product entity class
import org.springframework.data.mongodb.repository.MongoRepository;  // Spring Data MongoDB's repository interface

// Interface declaration: Defines a repository for Product entities, extending MongoRepository
// MongoRepo<Product, String> indicates that this repository manages Product entities with a String ID
public interface ProductRepository extends MongoRepository<Product, String> {
    // No methods are defined here because MongoRepository provides default CRUD operations
    // such as save(), findById(), findAll(), delete(), etc., for the Product entity
}