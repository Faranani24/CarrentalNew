package co.za.carrental.service;

import co.za.carrental.domain.Customer;
import co.za.carrental.factory.CustomerFactory;
import co.za.carrental.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    private CustomerRepository repository;
    private CustomerService service;
    private Customer customer;

    @BeforeEach
    void setUp() {
        repository = mock(CustomerRepository.class);
        service = new CustomerService(repository);
        customer = CustomerFactory.createCustomer(
                "CUST1001", "Bob", "Marley", "bmarley@gmail.com",
                "pass123", "0712345678", "BM12345678",
                List.of("Card", "PayPal")
        );
    }

    @Test
    void testCreate() {
        when(repository.save(customer)).thenReturn(customer);
        Customer created = service.create(customer);
        assertEquals(customer.getFirstName(), created.getFirstName());
    }

    @Test
    void testRead() {
        when(repository.findById("CUST1001")).thenReturn(Optional.of(customer));
        Optional<Customer> found = service.read("CUST1001");
        assertTrue(found.isPresent());
        assertEquals("Marley", found.get().getLastName());
    }

    @Test
    void testDelete() {
        service.delete("CUST1001");
        verify(repository, times(1)).deleteById("CUST1001");
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(customer));
        List<Customer> all = service.getAll();
        assertEquals(1, all.size());
    }
}

