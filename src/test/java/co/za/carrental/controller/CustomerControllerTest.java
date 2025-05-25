package co.za.carrental.controller;

import co.za.carrental.domain.Customer;
import co.za.carrental.factory.CustomerFactory;
import co.za.carrental.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = CustomerFactory.createCustomer(
                "cust1", "John", "Doe", "john@example.com",
                "pass123", "0812345678", "LIC001",
                List.of("Visa", "PayPal")
        );
        // Ensure bookings list is not null to avoid serialization errors
        if (customer.getBookings() != null) {
            return;
        }
        customer.setBookings(new ArrayList<>());
    }

    @Test
    void testCreateCustomer() throws Exception {
        Mockito.when(customerService.save(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("cust1"))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void testGetCustomerById() throws Exception {
        Mockito.when(customerService.findById("cust1")).thenReturn(customer);

        mockMvc.perform(get("/api/customers/cust1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testGetAllCustomers() throws Exception {
        Mockito.when(customerService.findAll()).thenReturn(List.of(customer));

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Mockito.doNothing().when(customerService).deleteById("cust1");

        mockMvc.perform(delete("/api/customers/cust1"))
                // Delete endpoint returns 204 No Content
                .andExpect(status().isNoContent());
    }
}
