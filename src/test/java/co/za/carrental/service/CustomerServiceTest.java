package co.za.carrental.service;

import co.za.carrental.domain.Customer;
import co.za.carrental.factory.CustomerFactory;
import co.za.carrental.repository.CustomerRepository;
import co.za.carrental.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void save_validCustomer_shouldReturnSavedCustomer() {
        Customer customerToSave = CustomerFactory.buildCustomer(
                "Lance", "Franks", "lance@example.com", "pass123", "0831234567", "L12345"
        );

        when(customerRepository.save(customerToSave)).thenReturn(customerToSave);

        Customer saved = customerService.save(customerToSave);

        assertNotNull(saved);
        assertEquals(customerToSave, saved);
        verify(customerRepository, times(1)).save(customerToSave);
    }
}