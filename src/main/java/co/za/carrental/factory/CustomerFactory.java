/*
 * CustomerFactory.java
 * Author: Lance Anthony Franks (230803865)
 * Date: 11 May 2025
 */

package co.za.carrental.factory;

import co.za.carrental.domain.Customer;

import java.util.List;
import java.util.UUID;

public class CustomerFactory {

    public static Customer buildCustomer(
            String firstName,
            String lastName,
            String email,
            String password,
            String phone,
            String licenseNumber,
            List<String> paymentMethods
    ) {
        // Basic null/empty validation
        if (firstName == null || lastName == null || email == null || password == null ||
                phone == null || licenseNumber == null || paymentMethods == null || paymentMethods.isEmpty()) {
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
                .setPaymentMethods(paymentMethods)
                .build();
    }

    public static Customer createCustomer(String number, String john, String adams, String mail, String password123, String number1, String cf54321, List<String> list) {

        return new Customer.Builder()
                .setCustomerId(number)
                .setFirstName(john)
                .setLastName(adams)
                .setEmail(mail)
                .setPassword(password123)
                .setPhone(number1)
                .setPaymentMethods(list)
                .build();
    }
}
