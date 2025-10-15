package co.za.carrental.controller;

import co.za.carrental.config.JwtUtil;
import co.za.carrental.domain.Customer;
import co.za.carrental.service.ICustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ICustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(ICustomerService customerService,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    // ------------------- SIGNUP -------------------
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Check if email already exists
        if (customerService.existsByEmail(email)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email already in use"));
        }

        // Get the plain password before encoding
        String plainPassword = request.get("password");

        // Build new customer with encoded password
        Customer newCustomer = new Customer.Builder()
                .setCustomerId(UUID.randomUUID().toString())
                .setFirstName(request.get("firstName"))
                .setLastName(request.get("lastName"))
                .setEmail(email)
                .setPassword(passwordEncoder.encode(plainPassword)) // Encrypt the password
                .setPhone(request.get("phone"))
                .setLicense(request.get("license"))
                .build();

        // Save the customer
        Customer savedCustomer = customerService.save(newCustomer);

        // Generate JWT token for the newly created user
        final UserDetails userDetails = userDetailsService.loadUserByUsername(savedCustomer.getEmail());
        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(Map.of(
                "user", savedCustomer,
                "token", token
        ));
    }

    // ------------------- LOGIN -------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            // Load user details and generate token
            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            final String token = jwtUtil.generateToken(userDetails);

            // Get customer details
            Optional<Customer> customerOpt = customerService.findByEmail(email);

            return ResponseEntity.ok(Map.of(
                    "user", customerOpt.get(),
                    "token", token
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }
    }
}