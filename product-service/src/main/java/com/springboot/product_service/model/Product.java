package com.springboot.product_service.model;

import lombok.AllArgsConstructor;  // Generates a constructor with all fields
import lombok.Builder;            // Enables the Builder pattern for object creation
import lombok.Data;               // Generates getters, setters, toString, equals, and hashCode methods
import lombok.NoArgsConstructor;  // Generates a no-args constructor
import org.springframework.data.annotation.Id;  // Marks the field as the primary key in MongoDB
import org.springframework.data.mongodb.core.mapping.Document;  // Indicates this class maps to a MongoDB collection

import java.math.BigDecimal;

// @Document annotation: Specifies that this class represents a MongoDB document, mapped to the "product" collection
@Document(value = "product")
// @AllArgsConstructor: Automatically creates a constructor with all fields (id, name, description)
@AllArgsConstructor
// @NoArgsConstructor: Creates a default constructor with no arguments
@NoArgsConstructor
// @Builder: Enables the Builder pattern, allowing flexible object creation (e.g., Product.builder().name("Laptop").build())
@Builder
// @Data: Lombok annotation that bundles @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor
@Data
// Class declaration: Defines the Product entity, representing a product in the MongoDB database
public class Product {
    // @Id: Marks this field as the unique identifier for the MongoDB document
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
}