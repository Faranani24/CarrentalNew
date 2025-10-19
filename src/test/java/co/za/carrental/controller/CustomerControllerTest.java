package co.za.carrental.controller;

import co.za.carrental.domain.Customer;
import co.za.carrental.service.ICustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICustomerService customerService;

    @Test
    @DisplayName("GET /api/customers should return 200 OK for ADMIN")
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void getAllCustomers_asAdmin() throws Exception {
        Customer admin = new Customer.Builder()
                .setCustomerId("1")
                .setEmail("admin@example.com")
                .setRole("ADMIN")
                .setPassword("password")
                .build();

        when(customerService.findByEmail("admin@example.com"))
                .thenReturn(Optional.of(admin));
        when(customerService.getAll()).thenReturn(List.of(admin));

        mockMvc.perform(get("/api/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("GET /api/customers should return 403 FORBIDDEN for regular user")
    @WithMockUser(username = "user@example.com", roles = {"CUSTOMER"})
    void getAllCustomers_asUser() throws Exception {
        Customer user = new Customer.Builder()
                .setCustomerId("2")
                .setEmail("user@example.com")
                .setRole("CUSTOMER")
                .setPassword("password")
                .build();

        when(customerService.findByEmail("user@example.com"))
                .thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/customers"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /api/customers should return 201 Created and save customer")
    void createCustomer() throws Exception {
        Customer customer = new Customer.Builder()
                .setCustomerId("1")
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("john@example.com")
                .setPassword("pass123")
                .setPhone("1234567890")
                .setLicense("LIC123")
                .build();

        when(customerService.save(any(Customer.class))).thenReturn(customer);

        String customerJson = """
            {
                "firstName": "John",
                "lastName": "Doe",
                "email": "john@example.com",
                "password": "pass123",
                "phone": "1234567890",
                "license": "LIC123"
            }
        """;

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.license").value("LIC123"));
    }

    @Test
    @DisplayName("GET /api/customers/me should return current user")
    @WithMockUser(username = "user@example.com", roles = {"CUSTOMER"})
    void getCurrentUser() throws Exception {
        Customer user = new Customer.Builder()
                .setCustomerId("1")
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("user@example.com")
                .setRole("CUSTOMER")
                .setPassword("password")
                .build();

        when(customerService.findByEmail("user@example.com"))
                .thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/customers/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@example.com"));
    }

    @Test
    @DisplayName("GET /api/customers/{id} should allow user to view their own profile")
    @WithMockUser(username = "user@example.com", roles = {"CUSTOMER"})
    void getOne_ownProfile() throws Exception {
        Customer user = new Customer.Builder()
                .setCustomerId("1")
                .setFirstName("John")
                .setLastName("Doe")
                .setEmail("user@example.com")
                .setRole("CUSTOMER")
                .setPassword("password")
                .build();

        when(customerService.findByEmail("user@example.com"))
                .thenReturn(Optional.of(user));
        when(customerService.findById("1"))
                .thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/customers/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@example.com"));
    }

    @Test
    @DisplayName("GET /api/customers/{id} should return 403 for other user's profile")
    @WithMockUser(username = "user@example.com", roles = {"CUSTOMER"})
    void getOne_otherProfile() throws Exception {
        Customer user = new Customer.Builder()
                .setCustomerId("1")
                .setEmail("user@example.com")
                .setRole("CUSTOMER")
                .setPassword("password")
                .build();

        Customer otherUser = new Customer.Builder()
                .setCustomerId("2")
                .setEmail("other@example.com")
                .setRole("CUSTOMER")
                .setPassword("password")
                .build();

        when(customerService.findByEmail("user@example.com"))
                .thenReturn(Optional.of(user));
        when(customerService.findById("2"))
                .thenReturn(Optional.of(otherUser));

        mockMvc.perform(get("/api/customers/2"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}