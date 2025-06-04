package com.springboot.product_service;

// Import statements: Bring in necessary classes and annotations for testing
import com.fasterxml.jackson.core.JsonProcessingException;  // Exception for JSON processing issues
import com.fasterxml.jackson.databind.ObjectMapper;  // Converts objects to/from JSON
import com.springboot.product_service.dto.ProductRequest;  // DTO for product creation requests
import com.springboot.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;  // Marks methods as test cases
import org.springframework.beans.factory.annotation.Autowired;  // Injects dependencies
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;  // Configures MockMvc for testing
import org.springframework.boot.test.context.SpringBootTest;  // Loads Spring Boot context for integration tests
import org.springframework.http.MediaType;  // Defines media types (e.g., JSON)
import org.springframework.test.context.DynamicPropertyRegistry;  // Allows dynamic property configuration
import org.springframework.test.context.DynamicPropertySource;  // Sets properties dynamically for tests
import org.springframework.test.web.servlet.MockMvc;  // Simulates HTTP requests for testing
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;  // Builds HTTP requests for MockMvc
import org.testcontainers.containers.MongoDBContainer;  // Manages MongoDB container for testing
import org.testcontainers.junit.jupiter.Container;  // Marks a container for Testcontainers
import org.testcontainers.junit.jupiter.Testcontainers;  // Enables Testcontainers for managing containers
import java.math.BigDecimal;  // Used for precise decimal values (e.g., price)
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;  // Matches HTTP response statuses

// @SpringBootTest: Loads the full Spring Boot application context for integration testing
@SpringBootTest
// @Testcontainers: Enables Testcontainers to manage containers (e.g., MongoDB) during tests
@Testcontainers
// @AutoConfigureMockMvc: Automatically configures MockMvc for testing web endpoints
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	// @Container: Marks this as a Testcontainers-managed container
	// Static MongoDBContainer: Starts a MongoDB container (version 4.4.2) for testing
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	// @Autowired: Injects MockMvc to simulate HTTP requests
	@Autowired
	private MockMvc mockMvc;

	// @Autowired: Injects ObjectMapper to convert objects to/from JSON
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;

	// @DynamicPropertySource: Dynamically sets the MongoDB URI property for the test
	// Links the Spring Data MongoDB URI to the Testcontainers MongoDB instance
	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	// @Test: Marks this method as a test case
	// Tests the product creation endpoint
	@Test
	void shouldCreateProduct() throws Exception {
		// Creates a ProductRequest object with test data
		ProductRequest productRequest = getProductRequest();
		// Converts the ProductRequest to a JSON string
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		// Performs a POST request to /api/products with the JSON payload
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)  // Sets the request content type to JSON
						.content(productRequestString))  // Sets the request body
				.andExpect(status().isCreated());  // Expects a 201 Created status
        Assertions.assertEquals(1, productRepository.findAll().size());
	}

	// Helper method: Creates a ProductRequest object with sample data for testing
	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Iphone")  // Sets the product name
				.description("iPhone ")  // Sets the product description
				.price(BigDecimal.valueOf(1200))  // Sets the product price
				.build();
	}
}