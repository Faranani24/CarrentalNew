// File: src/main/java/co/za/carrental/factory/CustomerFactory.java

package co.za.carrental.factory;

import co.za.carrental.domain.Customer;
import java.util.UUID;

public class CustomerFactory {

    public static Customer buildCustomer(
            String firstName,
            String lastName,
            String email,
            String password,
            String phone,
            String licenseNumber
    ) {
        // Basic null/empty validation
        if (firstName == null || lastName == null || email == null || password == null ||
                phone == null || licenseNumber == null) {
            throw new IllegalArgumentException("One or more required fields are null or empty");
        }

        // Auto-generate a unique customer ID
        String customerId = UUID.randomUUID().toString();

        return new Customer.Builder()
                .setCustomerId(customerId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPassword(password)
                .setPhone(phone)
                .setLicense(licenseNumber)
                .build();
    }
}