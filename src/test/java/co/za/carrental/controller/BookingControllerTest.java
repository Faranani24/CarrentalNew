package co.za.carrental.controller;

import co.za.carrental.api.dto.BookingRequest;
import co.za.carrental.domain.Booking;
import co.za.carrental.service.IBookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IBookingService bookingService;

    @Test
    void testCreate() throws Exception {
        BookingRequest bookingRequest = new BookingRequest();

        when(bookingService.createFromRequest(bookingRequest)).thenReturn(new Booking());

        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void testRead() throws Exception {
        when(bookingService.read("1")).thenReturn(Optional.of(new Booking()));

        mockMvc.perform(get("/api/bookings/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        Booking booking = new Booking();
        when(bookingService.update(booking)).thenReturn(booking);

        mockMvc.perform(put("/api/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(bookingService).delete("1");

        mockMvc.perform(delete("/api/bookings/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAll() throws Exception {
        when(bookingService.getAll()).thenReturn(Collections.singletonList(new Booking()));

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk());
    }
}
