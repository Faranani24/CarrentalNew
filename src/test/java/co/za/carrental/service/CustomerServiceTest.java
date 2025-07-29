package co.za.carrental.service;

import co.za.carrental.domain.Customer;
import co.za.carrental.factory.CustomerFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private ICustomerService customerService;

    @Test
    void saveAndFindById_shouldPersistAndRetrieveCustomer() {
        Customer customer = CustomerFactory.buildCustomer(
                "John", "Doe", "john.doe@example.com", "password123", "555-1234", "LIC123", Collections.singletonList("VISA")
        );
        Customer saved = customerService.save(customer);
        Optional<Customer> found = customerService.findById(saved.getCustomerId());
        assertTrue(found.isPresent());
        assertEquals(saved.getCustomerId(), found.get().getCustomerId());
    }

    @Test
    void update_shouldModifyCustomer() {
        Customer customer = CustomerFactory.buildCustomer(
                "Jane", "Smith", "jane.smith@example.com", "password456", "555-5678", "LIC456", Collections.singletonList("MASTERCARD")
        );
        Customer saved = customerService.save(customer);

        // Use builder to create updated customer
        Customer updatedCustomer = new Customer.Builder()
                .setCustomerId(saved.getCustomerId())
                .setFirstName(saved.getFirstName())
                .setLastName(saved.getLastName())
                .setEmail("jane.new@example.com")
                .setPassword(saved.getPassword())
                .setPhone(saved.getPhone())
                .setLicenseNumber(saved.getLicenseNumber())
                .setPaymentMethods(saved.getPaymentMethods())
                .build();

        Customer updated = customerService.update(updatedCustomer);
        assertEquals("jane.new@example.com", updated.getEmail());
    }

    @Test
    void deleteById_shouldRemoveCustomer() {
        Customer customer = CustomerFactory.buildCustomer(
                "Alice", "Brown", "alice.brown@example.com", "password789", "555-9012", "LIC789", Collections.singletonList("AMEX")
        );
        Customer saved = customerService.save(customer);
        customerService.deleteById(saved.getCustomerId());
        Optional<Customer> found = customerService.findById(saved.getCustomerId());
        assertFalse(found.isPresent());
    }
}
