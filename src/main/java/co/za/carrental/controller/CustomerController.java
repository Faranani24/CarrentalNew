package co.za.carrental.controller;

import co.za.carrental.domain.Customer;
import co.za.carrental.factory.CustomerFactory;
import co.za.carrental.service.ICustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        // FIX: Added "USER" as the default role argument, aligning with the updated CustomerFactory
        Customer newCustomer = CustomerFactory.buildCustomer(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPassword(),
                "USER"
        );

        Customer saved = customerService.save(newCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getOne(@PathVariable String id, Authentication authentication) {
        String userEmail = authentication.getName();
        Customer currentUser = customerService.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Customer> customerOpt = customerService.findById(id);

        if (customerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Customer customer = customerOpt.get();

        // Only admins or the customer themselves can view the profile
        if (!"ADMIN".equals(currentUser.getRole()) &&
                !customer.getCustomerId().equals(currentUser.getCustomerId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(customer);
    }

    @GetMapping("/me")
    public ResponseEntity<Customer> getCurrentUser(Authentication authentication) {
        String userEmail = authentication.getName();
        return customerService.findByEmail(userEmail)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable String id,
                                           @RequestBody Customer customer,
                                           Authentication authentication) {
        String userEmail = authentication.getName();
        Customer currentUser = customerService.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Customer> existing = customerService.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Only admins or the customer themselves can update the profile
        if (!"ADMIN".equals(currentUser.getRole()) &&
                !existing.get().getCustomerId().equals(currentUser.getCustomerId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Customer updatedCustomer = new Customer.Builder()
                .setCustomerId(existing.get().getCustomerId())
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setEmail(customer.getEmail())
                .setPassword(customer.getPassword())
                .setRole(existing.get().getRole()) // Preserve existing role
                .build();

        Customer updated = customerService.update(updatedCustomer);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id, Authentication authentication) {
        String userEmail = authentication.getName();
        Customer currentUser = customerService.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Customer> customerOpt = customerService.findById(id);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Only admins or the customer themselves can delete the account
        if (!"ADMIN".equals(currentUser.getRole()) &&
                !customerOpt.get().getCustomerId().equals(currentUser.getCustomerId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        customerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAll(Authentication authentication) {
        String userEmail = authentication.getName();
        Customer currentUser = customerService.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Only admins can view all customers
        if (!"ADMIN".equals(currentUser.getRole())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(customerService.getAll());
    }
}
