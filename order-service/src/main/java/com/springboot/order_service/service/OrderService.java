package com.springboot.order_service.service;

// Import statements: Bring in necessary classes and annotations for the service.
import com.springboot.order_service.dto.OrderLineItemsDto; // DTO for order line items.
import com.springboot.order_service.dto.OrderRequest; // DTO for the order request payload.
import com.springboot.order_service.model.Order; // Entity representing an order.
import com.springboot.order_service.model.OrderLineItems; // Entity representing individual items in an order.
import com.springboot.order_service.repository.OrderRepository; // Repository for database operations on Order entities.
import lombok.RequiredArgsConstructor; // Lombok annotation to auto-generate a constructor for final fields.
import org.springframework.stereotype.Service; // Marks this class as a Spring service component.
import org.springframework.transaction.annotation.Transactional; // Enables transactional behavior for methods in this class.
import java.util.List; // For handling lists of objects.
import java.util.UUID; // For generating unique order numbers.

// Spring Service annotation: Marks this class as a service bean, making it eligible for dependency injection.
@Service
// Lombok annotation: Generates a constructor that injects the final OrderRepository field.
@RequiredArgsConstructor
// Transactional annotation: Ensures all methods in this class run within a transaction, rolling back on errors.
@Transactional
public class OrderService {

    // Final field: Dependency-injected OrderRepository for database operations.
    private final OrderRepository orderRepository;

    // Method to place a new order based on the provided OrderRequest DTO.
    public void placeOrder(OrderRequest orderRequest) {
        // Create a new Order entity instance.
        Order order = new Order();
        // Set a unique order number using a randomly generated UUID.
        order.setOrderNumber(UUID.randomUUID().toString());

        // Convert the list of OrderLineItemsDto from the request to a list of OrderLineItems entities.
        // Stream through the DTO list, map each DTO to an entity using mapToDto, and collect into a List.
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto) // Calls the mapToDto method to transform each DTO.
                .toList(); // Collects the results into an unmodifiable List.
        // Set the list of order line items to the order entity.
        order.setOrderLineItemsList(orderLineItems);

        // Save the order (and its associated line items) to the database using the repository.
        orderRepository.save(order);
    }

    // Private helper method to map an OrderLineItemsDto to an OrderLineItems entity.
    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        // Create a new OrderLineItems entity instance.
        OrderLineItems orderLineItems = new OrderLineItems();
        // Set the price from the DTO to the entity.
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        // Set the quantity from the DTO to the entity.
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        // Set the SKU code from the DTO to the entity.
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

        // Return the fully populated OrderLineItems entity.
        return orderLineItems;
    }
}