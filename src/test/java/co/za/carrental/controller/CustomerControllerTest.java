// File: src/test/java/co/za/carrental/controller/CustomerControllerTest.java
package co.za.carrental.controller;

import co.za.carrental.domain.Customer;
import co.za.carrental.service.ICustomerService; // You might still need this for some setups, but less critical here
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest; // <--- CHANGE THIS IMPORT
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // <--- ADD THIS IMPORT
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print; // Optional: To print request/response details

@SpringBootTest // <--- Use this annotation
@AutoConfigureMockMvc // <--- Add this annotation to enable MockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // You no longer need @MockBean private ICustomerService customerService;
    // Spring will now inject the *real* ICustomerService implementation.

    @Test
    @DisplayName("GET /api/customers should return 200 OK")
    void getAllCustomers() throws Exception {
        mockMvc.perform(get("/api/customers"))
                .andDo(print()) // Optional: Prints the request and response for debugging
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        // You might not want to assert on empty list if your test setup adds data
        // .andExpect(jsonPath("$").isArray())
        // .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("POST /api/customers should return 201 Created") // Updated expectation
    void createCustomer() throws Exception {
        String customerJson = """
            {
                "customerId": "C001",
                "firstName": "John",
                "lastName": "Doe",
                "email": "john@example.com",
                "password": "pass123",
                "phone": "1234567890",
                "licenseNumber": "LIC123",
                "paymentMethods": []
            }
        """;
        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andDo(print()) // Optional: Prints the request and response for debugging
                .andExpect(status().isCreated()) // <--- Expect 201 Created for POST
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value("C001"))
                .andExpect(jsonPath("$.firstName").value("John"));
        // Add more assertions based on the exact response from your real service/DB
    }

    // IMPORTANT: For integration tests, consider adding @AfterEach or @BeforeEach
    // methods if you need to clean up database state between tests.
    // For example, if you use an in-memory database like H2 for tests, it's often reset per test.
    // If using a real database, you might need @Transactional and/or specific cleanup.
}