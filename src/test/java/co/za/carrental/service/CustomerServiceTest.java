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
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService service;


    @Autowired
    private CustomerRepository repository;


    private Customer customer;

    @BeforeEach
    void setUp() {
        repository.deleteAll(); // Clear data from previous tests

        customer = CustomerFactory.createCustomer(
                "CUST1001", "Bob", "Marley", "bmarley@gmail.com",
                "pass123", "0712345678", "BM12345678",
                List.of("Card", "PayPal")
        );
        if (customer.getBookings() == null) {
            customer.setBookings(new java.util.ArrayList<>());
        }

    }

    @Test
    void testSave() {
        Customer created = service.save(customer);
        assertNotNull(created);
        assertEquals(customer.getCustomerId(), created.getCustomerId());
        assertEquals(customer.getFirstName(), created.getFirstName());

        Optional<Customer> foundInDb = repository.findById(customer.getCustomerId());
        assertTrue(foundInDb.isPresent());
        assertEquals(customer.getFirstName(), foundInDb.get().getFirstName());
    }

    @Test
    void testFindById() {
        repository.save(customer);

        Optional<Customer> found = service.findById("CUST1001");
        assertTrue(found.isPresent());
        assertEquals("Marley", found.get().getLastName());
    }

    @Test
    void testDeleteById() {
        repository.save(customer);

        service.deleteById("CUST1001");

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
        repository.save(customer);

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
        Customer nonExistentCustomer = new Customer.Builder("NONEXIST", "Non", "Existent", "non@gmail.com")
                .build();

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.update(nonExistentCustomer);
        });
        assertTrue(thrown.getMessage().contains("does not exist"));
        assertFalse(repository.findById("NONEXIST").isPresent());
    }

    @Test
    void testFindById_NotFound() {
        Optional<Customer> found = service.findById("NONEXIST");
        assertFalse(found.isPresent());
    }
}