package co.za.carrental.controller;

import co.za.carrental.api.dto.BookingRequest;
import co.za.carrental.domain.Booking;
import co.za.carrental.domain.Customer;
import co.za.carrental.service.IBookingService;
import co.za.carrental.service.ICustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IBookingService bookingService;

    @MockBean
    private ICustomerService customerService;

    @Test
    @WithMockUser(username = "test@example.com", roles = {"CUSTOMER"})
    void testCreate() throws Exception {
        when(bookingService.createFromRequest(any(BookingRequest.class)))
                .thenReturn(new Booking());

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setFrom("2025-05-10");
        bookingRequest.setTo("2025-05-11");

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void testRead() throws Exception {
        Customer admin = new Customer.Builder()
                .setCustomerId("1")
                .setEmail("admin@example.com")
                .setRole("ADMIN")
                .build();

        when(customerService.findByEmail("admin@example.com"))
                .thenReturn(Optional.of(admin));
        when(bookingService.read(eq("1"))).thenReturn(Optional.of(new Booking()));

        mockMvc.perform(get("/api/bookings/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void testUpdate() throws Exception {
        Customer admin = new Customer.Builder()
                .setCustomerId("1")
                .setEmail("admin@example.com")
                .setRole("ADMIN")
                .build();

        Booking existing = new Booking();
        existing.setBookingId("1");
        existing.setCustomer(admin);

        when(customerService.findByEmail("admin@example.com"))
                .thenReturn(Optional.of(admin));
        when(bookingService.read("1")).thenReturn(Optional.of(existing));
        when(bookingService.update(any(Booking.class))).thenReturn(existing);

        mockMvc.perform(put("/api/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existing)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void testDelete() throws Exception {
        Customer admin = new Customer.Builder()
                .setCustomerId("1")
                .setEmail("admin@example.com")
                .setRole("ADMIN")
                .build();

        Booking booking = new Booking();
        booking.setCustomer(admin);

        when(customerService.findByEmail("admin@example.com"))
                .thenReturn(Optional.of(admin));
        when(bookingService.read("1")).thenReturn(Optional.of(booking));
        doNothing().when(bookingService).delete("1");

        mockMvc.perform(delete("/api/bookings/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin@example.com", roles = {"ADMIN"})
    void testGetAll() throws Exception {
        Customer admin = new Customer.Builder()
                .setCustomerId("1")
                .setEmail("admin@example.com")
                .setRole("ADMIN")
                .build();

        when(customerService.findByEmail("admin@example.com"))
                .thenReturn(Optional.of(admin));
        when(bookingService.getAll()).thenReturn(List.of(new Booking()));

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk());
    }
}