package co.za.carrental.factory;

import co.za.carrental.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class BookingFactoryTest {

    @Test
    void createBooking_ValidArguments_ReturnsBookingObject() {


        Customer customer = new Customer.Builder()
                .setCustomerId("C001")
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("john.doe@example.com")
                .setPassword("password123")
                .setPhone("0821234567")
                .setLicense("LIC123")
                .build();


    }

}