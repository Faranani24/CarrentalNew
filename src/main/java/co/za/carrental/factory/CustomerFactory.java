package co.za.carrental.factory;

import co.za.carrental.domain.Customer;
import java.util.UUID;

public class CustomerFactory {

    public static Customer buildCustomer(
            String firstName,
            String lastName,
            String email,
            String password,
            String role // Added 'role' parameter to support the CustomerController logic
    ) {

        // Validate all required fields, including the new 'role'
        if (firstName == null || lastName == null || email == null || password == null || role == null) {
            throw new IllegalArgumentException("One or more required fields are null or empty");
        }


        String customerId = UUID.randomUUID().toString();

        return new Customer.Builder()
                .setCustomerId(customerId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .setRole(role) // Set the role
                .build();
    }
}
