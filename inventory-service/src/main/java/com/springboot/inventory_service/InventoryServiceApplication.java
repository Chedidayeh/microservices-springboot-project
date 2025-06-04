package com.springboot.inventory_service;

// Import statements: Bring in necessary classes and annotations for the application.
import com.springboot.inventory_service.model.Inventory; // Entity representing an inventory item.
import com.springboot.inventory_service.repository.InventoryRepository; // Repository for database operations on Inventory entities.
import org.springframework.boot.CommandLineRunner; // Interface for running code after application startup.
import org.springframework.boot.SpringApplication; // Class to bootstrap the Spring Boot application.
import org.springframework.boot.autoconfigure.SpringBootApplication; // Marks this class as the main Spring Boot application.
import org.springframework.context.annotation.Bean; // Annotation to define Spring beans.

// Spring Boot application annotation: Marks this class as the entry point for the Spring Boot application.
// Enables autoconfiguration, component scanning, and configuration within the package.
@SpringBootApplication
public class InventoryServiceApplication {

	// Main method: Entry point of the application, starts the Spring Boot application.
	public static void main(String[] args) {
		// Boots up the Spring application context, initializing all beans and configurations.
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	// Bean definition: Defines a CommandLineRunner bean to execute code after the application starts.
	// This method is used to preload initial data into the database.
	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		// Returns a lambda implementation of CommandLineRunner, which runs on application startup.
		return args -> {
			// Create a new Inventory entity instance for the first item.
			Inventory inventory = new Inventory();
			// Set the SKU code for the first inventory item.
			inventory.setSkuCode("chedi");
			// Set the quantity for the first inventory item.
			inventory.setQuantity(100);

			// Create a new Inventory entity instance for the second item.
			Inventory inventory1 = new Inventory();
			// Bug: Incorrectly sets the SKU code on the first inventory object instead of inventory1.
			inventory1.setSkuCode("chedi1"); // Should be inventory1.setSkuCode("chedi1");
			// Bug: Incorrectly sets the quantity on the first inventory object instead of inventory1.
			inventory1.setQuantity(0); // Should be inventory1.setQuantity(0);

			// Save the first inventory item to the database using the repository.
			inventoryRepository.save(inventory);
			// Save the second inventory item to the database (but it has no updated values due to the bug).
			inventoryRepository.save(inventory1);
		};
	}
}