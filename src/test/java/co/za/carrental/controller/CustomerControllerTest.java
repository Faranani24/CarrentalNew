package co.za.carrental.controller;

import co.za.carrental.domain.Customer;
import co.za.carrental.factory.CustomerFactory; // Still useful for common creation patterns
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
import java.util.Optional;

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
        // Using the Builder pattern directly for more control and readability in tests
        customer = new Customer.Builder("cust1", "John", "Doe", "john@example.com")
                .password("password123")
                .phoneNumber("0812345678")
                .licenseNumber("LIC001")
                .paymentMethods(List.of("Visa", "PayPal"))
                .bookings(new ArrayList<>()) // Ensure bookings is initialized via builder
                .build();

        // No need for the null check now if the builder initializes collections
        // if (customer.getBookings() == null) {
        //     customer.setBookings(new ArrayList<>());
        // }
    }

    @Test
    void testCreateCustomer() throws Exception {
        Mockito.when(customerService.save(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId").value("cust1"))
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void testGetCustomerById() throws Exception {
        Mockito.when(customerService.findById("cust1")).thenReturn(Optional.of(customer));

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
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        // Using the builder to create an updated customer instance
        Customer updatedCustomer = new Customer.Builder("cust1", "Jane", "Doe", "jane@example.com")
                .password("newpass")
                .phoneNumber("0812345679")
                .licenseNumber("LIC002")
                .paymentMethods(List.of("Credit Card"))
                .bookings(new ArrayList<>()) // Ensure bookings are handled
                .build();

        Mockito.when(customerService.update(any(Customer.class))).thenReturn(updatedCustomer);

        mockMvc.perform(put("/api/customers/cust1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.email").value("jane@example.com")); // Corrected the email
    }

    @Test
    void testUpdateCustomer_NotFound() throws Exception {
        Customer nonExistentCustomer = new Customer.Builder("nonExistent", "Alice", "Smith", "alice@example.com")
                .password("pass")
                .phoneNumber("08123")
                .licenseNumber("LICXYZ")
                .paymentMethods(List.of("Cash"))
                .build();

        Mockito.when(customerService.update(any(Customer.class)))
                .thenThrow(new IllegalArgumentException("Cannot update customer: ID is null or customer does not exist."));

        mockMvc.perform(put("/api/customers/nonExistent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nonExistentCustomer)))
                .andExpect(status().isNotFound());
    }
}