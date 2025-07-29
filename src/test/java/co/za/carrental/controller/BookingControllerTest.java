package co.za.carrental.controller;

import co.za.carrental.domain.Booking;
import co.za.carrental.service.IBookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class StubConfig {
        @Bean
        public IBookingService bookingService() {
            return new IBookingService() {
                @Override
                public void customBookingLogic() {}

                @Override
                public Booking save(Booking booking) { return null; }

                @Override
                public Optional<Booking> findById(String bookingId) { return Optional.empty(); }

                @Override
                public void deleteById(String bookingId) {}

                @Override
                public Booking create(Booking booking) { return booking; }

                @Override
                public Optional<Booking> read(String id) { return Optional.of(new Booking()); }

                @Override
                public Booking update(Booking booking) { return booking; }

                @Override
                public void delete(String id) {}

                @Override
                public java.util.List<Booking> getAll() { return Collections.singletonList(new Booking()); }

                @Override
                public java.util.List<Booking> findAll() { return Collections.singletonList(new Booking()); }
            };
        }
    }

    @Test
    void testCreate() throws Exception {
        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void testRead() throws Exception {
        mockMvc.perform(get("/api/bookings/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        mockMvc.perform(put("/api/bookings/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/api/bookings/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAll() throws Exception {
        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk());
    }
}
