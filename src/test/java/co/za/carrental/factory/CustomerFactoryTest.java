package co.za.carrental.factory;

import co.za.carrental.domain.Customer;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CustomerFactoryTest {

    @Test
    void createCustomer() {
        Customer customer = CustomerFactory.createCustomer(
                "2301", "John", "Adams", "adamsjohn@gmail.com", "password123",
                "0823456789", "CF54321", Arrays.asList("Card", "EFT")
        );

        assertNotNull(customer);
        assertEquals("John", customer.getFirstName());
        assertEquals("Adams", customer.getLastName());
        assertTrue(customer.getPaymentMethods().contains("Card"));
    }
}

