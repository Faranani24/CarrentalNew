package co.za.carrental.service;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.BookingStatus;
import co.za.carrental.domain.Customer;
import co.za.carrental.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    private BookingRepository repository;
    private BookingService service;
    private Booking booking;

    @BeforeEach
    void setUp() {
        repository = mock(BookingRepository.class);
        service = new BookingService(repository);

        Customer customer = new Customer.Builder()
                .setCustomerId("CUST1001")
                .setFirstName("Lance")
                .build();

        booking = new Booking.Builder()
                .setBookingId("B001")
                .setStartDate(new Date())
                .setEndDate(new Date())
                .setTotalCost(1500.0f)
                .setStatus(BookingStatus.CONFIRMED) // Replace with your Enum type
                .build();
    }

    @Test
    void testCreate() {
        when(repository.save(booking)).thenReturn(booking);
        Booking created = service.create(booking);
        assertEquals("B001", created.getBookingId());
    }

    @Test
    void testRead() {
        when(repository.findById("B001")).thenReturn(Optional.of(booking));
        Optional<Booking> result = service.read("B001");
        assertTrue(result.isPresent());
        assertEquals(booking.getTotalCost(), result.get().getTotalCost());
    }

    @Test
    void testDelete() {
        service.delete("B001");
        verify(repository, times(1)).deleteById("B001");
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(booking));
        List<Booking> result = service.getAll();
        assertEquals(1, result.size());
    }
}

