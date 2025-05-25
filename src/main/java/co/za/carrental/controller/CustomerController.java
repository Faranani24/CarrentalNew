package co.za.carrental.controller;

import co.za.carrental.domain.Customer;
import co.za.carrental.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional; // This is used by .map and .orElse in read method

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping // For creating a new customer
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        Customer createdCustomer = service.save(customer); // Use save as per your service
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED); // FIX 1: Return 201 Created
    }

    @GetMapping("/{id}") // For reading a customer by ID
    public ResponseEntity<Customer> read(@PathVariable String id) {
        return service.findById(id) // Use findById as per your service
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping // For getting all customers
    public ResponseEntity<List<Customer>> getAll() { // FIX 2: Consistent ResponseEntity return type
        List<Customer> customers = service.findAll(); // Use findAll as per your service
        return ResponseEntity.ok(customers); // Return 200 OK
    }

    @PutMapping("/{id}") // FIX 3: Added an update method
    public ResponseEntity<Customer> update(@PathVariable String id, @RequestBody Customer customer) {
        // Optional: Ensure ID in path matches ID in body
        if (!id.equals(customer.getCustomerId())) { // Assuming customer has getCustomerId()
            throw new IllegalArgumentException("Customer ID in path must match customer ID in request body.");
        }
        try {
            Customer updatedCustomer = service.update(customer); // Use your specific update method
            return ResponseEntity.ok(updatedCustomer); // Return 200 OK
        } catch (IllegalArgumentException e) {
            // Handle the case where the customer to update doesn't exist (e.g., from service.update)
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}") // For deleting a customer by ID
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id); // Use deleteById as per your service
        return ResponseEntity.noContent().build(); // Return 204 No Content for successful deletion
    }
}