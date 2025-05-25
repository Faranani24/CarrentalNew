package co.za.carrental.factory;

import co.za.carrental.domain.Customer;
import co.za.carrental.domain.Booking; // Assuming you might use this if Customer has bookings
import java.util.List;

public class CustomerFactory {
    public static Customer createCustomer(String customerId, String firstName, String lastName, String email,
                                          String password, String phoneNumber, String licenseNumber,
                                          List<String> paymentMethods) {
        return new Customer.Builder(customerId, firstName, lastName, email)
                .password(password)
                .phoneNumber(phoneNumber)
                .licenseNumber(licenseNumber)
                .paymentMethods(paymentMethods)
                .build();
    }

    // Optional: Add a more flexible factory method if you don't always need all fields
    public static Customer createCustomerWithMinimalDetails(String customerId, String firstName, String lastName, String email) {
        return new Customer.Builder(customerId, firstName, lastName, email).build();
    }
}