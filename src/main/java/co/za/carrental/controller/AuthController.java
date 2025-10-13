// File: src/main/java/co/za/carrental/controller/AuthController.java
package co.za.carrental.controller;

import co.za.carrental.domain.Customer;
import co.za.carrental.service.ICustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ICustomerService customerService;

    public AuthController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    // ------------------- SIGNUP -------------------
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Check if email already exists
        if (customerService.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email already in use"));
        }

        // Build new customer
        Customer newCustomer = new Customer.Builder()
                .setCustomerId(UUID.randomUUID().toString())
                .setFirstName(request.get("firstName"))
                .setLastName(request.get("lastName"))
                .setEmail(email)
                .setPassword(request.get("password")) // TODO: hash password in production
                .setPhone(request.get("phone"))
                .setLicense(request.get("license"))
                .build();

        Customer savedCustomer = customerService.save(newCustomer);

        // Return saved customer
        return ResponseEntity.ok(Map.of(
                "user", savedCustomer,
                "token", "mock-jwt-token" // placeholder token for frontend
        ));
    }

    // ------------------- LOGIN -------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Optional<Customer> customerOpt = customerService.findByEmail(email);
        if (customerOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
        }

        Customer customer = customerOpt.get();

        // Simple password check (replace with hashed password check in production)
        if (!customer.getPassword().equals(password)) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
        }

        // Return customer and mock token
        return ResponseEntity.ok(Map.of(
                "user", customer,
                "token", "mock-jwt-token"
        ));
    }
}
