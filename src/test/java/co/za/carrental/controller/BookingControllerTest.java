package co.za.carrental.controller;

import co.za.carrental.api.dto.BookingRequest;
import co.za.carrental.domain.Booking;
import co.za.carrental.service.IBookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

    @Test
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
    void testRead() throws Exception {
        when(bookingService.read(eq("1"))).thenReturn(Optional.of(new Booking()));

        mockMvc.perform(get("/api/bookings/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        Booking existing = new Booking();
        existing.setBookingId("1");

        when(bookingService.read("1")).thenReturn(Optional.of(existing));
        when(bookingService.update(any(Booking.class))).thenReturn(existing);

        mockMvc.perform(put("/api/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existing)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        when(bookingService.read("1")).thenReturn(Optional.of(new Booking()));
        doNothing().when(bookingService).delete("1");

        mockMvc.perform(delete("/api/bookings/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAll() throws Exception {
        when(bookingService.getAll()).thenReturn(List.of(new Booking()));

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk());
    }
}