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


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String plainPassword = request.get("password");

            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Email is required"));
            }
            if (plainPassword == null || plainPassword.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("message", "Password is required"));
            }

            if (customerService.existsByEmail(email)) {
                return ResponseEntity.badRequest().body(Map.of("message", "Email already in use"));
            }

            String role = "CUSTOMER"; // Default role
            String[] adminEmails = {"admin@carrental.com", "admin@gmail.com", "tester@carrental.com"}; // Add your admin emails here
            for (String adminEmail : adminEmails) {
                if (email.equalsIgnoreCase(adminEmail)) {
                    role = "ADMIN";
                    break;
                }
            }


            Customer newCustomer = new Customer.Builder()
                    .setCustomerId(UUID.randomUUID().toString())
                    .setFirstName(request.getOrDefault("firstName", ""))
                    .setLastName(request.getOrDefault("lastName", ""))
                    .setEmail(email)
                    .setPassword(passwordEncoder.encode(plainPassword))
                    .setPhone(request.getOrDefault("phone", ""))
                    .setLicense(request.getOrDefault("license", ""))
                    .setRole(role)
                    .build();

            Customer savedCustomer = customerService.save(newCustomer);

            final UserDetails userDetails = userDetailsService.loadUserByUsername(savedCustomer.getEmail());
            final String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.ok(Map.of(
                    "user", savedCustomer,
                    "token", token
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                    Map.of("message", "Signup failed: " + e.getMessage())
            );
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            final String token = jwtUtil.generateToken(userDetails);

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