// File: src/test/java/co/za/carrental/factory/BookingFactoryTest.java

package co.za.carrental.factory;

import co.za.carrental.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookingFactoryTest {

    @Test
    void createBooking_ValidArguments_ReturnsBookingObject() {
        // ... (other parts of the test)

        Customer customer = new Customer.Builder()
                .setCustomerId("C001")
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("john.doe@example.com")
                .setPassword("password123")
                .setPhone("0821234567")
                .setLicense("LIC123")
                .build();

        // ... (rest of the test)
    }

    // You will need to repeat this for all other test methods in this file
    // that use the Customer.Builder.
}