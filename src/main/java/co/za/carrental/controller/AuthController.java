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

    public AuthController(ICustomerService customerService, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
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

        // Build new customer
        Customer newCustomer = new Customer.Builder()
                .setCustomerId(UUID.randomUUID().toString())
                .setFirstName(request.get("firstName"))
                .setLastName(request.get("lastName"))
                .setEmail(email)
                .setPassword(passwordEncoder.encode(request.get("password"))) // Encrypt the password
                .setPhone(request.get("phone"))
                .setLicense(request.get("license"))
                .build();

        Customer savedCustomer = customerService.save(newCustomer);

        // --- CHANGE IS HERE ---
        // Since the user is newly created, we can trust their details
        // to generate the first token without re-authenticating.
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

        // The login process still uses the authentication manager to validate credentials
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String token = jwtUtil.generateToken(userDetails);

        Optional<Customer> customerOpt = customerService.findByEmail(email);

        return ResponseEntity.ok(Map.of(
                "user", customerOpt.get(),
                "token", token
        ));
    }
}