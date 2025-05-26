package co.za.carrental.controller;

import co.za.carrental.domain.Customer;
import co.za.carrental.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Customer createdCustomer = service.save(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> read(@PathVariable String id) {
        return service.findById(id) // Use findById as per your service
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping //
    public ResponseEntity<List<Customer>> getAll() {
        List<Customer> customers = service.findAll();
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable String id, @RequestBody Customer customer) {

        if (!id.equals(customer.getCustomerId())) {
            throw new IllegalArgumentException("Customer ID in path must match customer ID in request body.");
        }
        try {
            Customer updatedCustomer = service.update(customer);
            return ResponseEntity.ok(updatedCustomer);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}") // For deleting a customer by ID
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id); // Use deleteById as per your service
        return ResponseEntity.noContent().build(); // Return 204 No Content for successful deletion
    }
}