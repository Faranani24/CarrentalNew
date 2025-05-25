package co.za.carrental.service;

import co.za.carrental.domain.Customer;
import co.za.carrental.factory.CustomerFactory;
import co.za.carrental.repository.CustomerRepository; // Keep this import
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean; // REMOVE THIS IMPORT AND ANNOTATION

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*; // You might still use Mockito for other services,
// but not for CustomerRepository in this case.
// If no other mocks, you can remove Mockito imports.

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService service;

    // NO @MockBean HERE - Spring will inject the actual CustomerRepository
    // which will be backed by the H2 in-memory database
    @Autowired // You might still want to autowire it for setup/teardown if needed,
    // but not for mocking its behavior in the tests
    private CustomerRepository repository;


    private Customer customer;

    @BeforeEach
    void setUp() {
        // Ensure the database is clean before each test
        // This is good practice for integration-style tests
        repository.deleteAll(); // Clear data from previous tests

        customer = CustomerFactory.createCustomer(
                "CUST1001", "Bob", "Marley", "bmarley@gmail.com",
                "pass123", "0712345678", "BM12345678",
                List.of("Card", "PayPal")
        );
        if (customer.getBookings() == null) {
            customer.setBookings(new java.util.ArrayList<>());
        }
        // No need to reset mocks here, as the repository is real
    }

    @Test
    void testSave() {
        // Now, when service.save() is called, it will use the actual repository
        // which will save to the H2 database.
        Customer created = service.save(customer);
        assertNotNull(created);
        assertEquals(customer.getCustomerId(), created.getCustomerId());
        assertEquals(customer.getFirstName(), created.getFirstName());

        // You can now verify that the data exists in the "real" (H2) database
        Optional<Customer> foundInDb = repository.findById(customer.getCustomerId());
        assertTrue(foundInDb.isPresent());
        assertEquals(customer.getFirstName(), foundInDb.get().getFirstName());
    }

    @Test
    void testFindById() {
        // First, save the customer to the H2 database
        repository.save(customer);

        Optional<Customer> found = service.findById("CUST1001");
        assertTrue(found.isPresent());
        assertEquals("Marley", found.get().getLastName());
    }

    @Test
    void testDeleteById() {
        repository.save(customer); // Ensure it exists before trying to delete

        service.deleteById("CUST1001");

        // Verify it's no longer in the H2 database
        assertFalse(repository.findById("CUST1001").isPresent());
    }

    @Test
    void testFindAll() {
        repository.save(customer);
        List<Customer> all = service.findAll();
        assertNotNull(all);
        assertEquals(1, all.size());
        assertEquals(customer.getCustomerId(), all.get(0).getCustomerId());
    }

    @Test
    void testUpdate() {
        repository.save(customer); // Save the original customer first

        Customer updatedCustomer = new Customer.Builder("CUST1001", "Bob", "Marley", "updated@gmail.com")
                .password("newpass")
                .phoneNumber("0712345679")
                .licenseNumber("BM12345678")
                .paymentMethods(List.of("Card"))
                .build();

        Customer result = service.update(updatedCustomer);
        assertNotNull(result);
        assertEquals("updated@gmail.com", result.getEmail());

        // Verify the update actually happened in the H2 database
        Optional<Customer> updatedInDb = repository.findById("CUST1001");
        assertTrue(updatedInDb.isPresent());
        assertEquals("updated@gmail.com", updatedInDb.get().getEmail());
    }

    @Test
    void testUpdate_CustomerNotFound() {
        // No need to save anything as we're testing the not found scenario
        Customer nonExistentCustomer = new Customer.Builder("NONEXIST", "Non", "Existent", "non@gmail.com")
                .build();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.update(nonExistentCustomer);
        });
        assertTrue(thrown.getMessage().contains("does not exist"));
        // Ensure no save happened in the real repository
        assertFalse(repository.findById("NONEXIST").isPresent());
    }

    @Test
    void testFindById_NotFound() {
        // No need to save anything as we're testing the not found scenario
        Optional<Customer> found = service.findById("NONEXIST");
        assertFalse(found.isPresent());
    }
}