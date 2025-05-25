package co.za.carrental.service;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.Car;
import co.za.carrental.domain.Customer;
import co.za.carrental.factory.BookingFactory;
import org.junit.jupiter.api.*;
import co.za.carrental.domain.BookingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BookingServiceTest {

    @Autowired
    private IBookingService iBookingService; // Assuming you have a BookingService and IBookingService

    @Autowired
    private CustomerService customerService; // This is the service in question

    private static final String BOOKING_ID = "b001";
    private static final String CUSTOMER_ID = "cust001";

    // Make 'booking' non-static if it changes per test, or ensure it's handled carefully.
    // Given @TestMethodOrder, static is okay if tests are truly ordered and dependent.
    private static Booking booking;

    @BeforeAll
    static void setup() {
        // Static setup data if needed
    }

    @Test
    @Order(1)
    void testCreateCustomerAndBooking(Car car) {
        Customer customer = new Customer.Builder(
                CUSTOMER_ID, "Alice", "Smith", "alice@example.com")
                .password("pass123") // Assuming password field
                .phoneNumber("0123456789") // Assuming phone number field
                .licenseNumber("LIC123") // Assuming license number field
                .paymentMethods(List.of("Card", "Cash")) // Assuming payment methods field
                .build();

        // FIX: Changed customerService.create(customer) to customerService.save(customer)
        customerService.save(customer);

        booking = BookingFactory.createBooking(
                BOOKING_ID,
                new Date(),
                new Date(System.currentTimeMillis() + 86400000), // 1 day later
                1200.0f,
                BookingStatus.CONFIRMED,
                customer, car);

        // Assuming your BookingService also has a 'create' method (or 'save' as per convention)
        Booking created = iBookingService.create(booking); // Or bookingService.save(booking);
        assertNotNull(created);
        assertEquals(BOOKING_ID, created.getBookingId());
    }

    @Test
    @Order(2)
    void testReadBooking() {
        // Assuming your BookingService has a 'read' method that returns Optional<Booking>
        Optional<Booking> read = iBookingService.read(BOOKING_ID);
        assertTrue(read.isPresent());
        assertEquals(BOOKING_ID, read.get().getBookingId());
    }

    @Test
    @Order(3)
    void testUpdateBooking() {
        Booking updated = new Booking.Builder()
                .setBookingId(BOOKING_ID)
                .setStartDate(booking.getStartDate()) // Use the start date from the created booking
                .setEndDate(new Date(System.currentTimeMillis() + 172800000)) // +2 days from now
                .setTotalCost(1500.0f)
                .setStatus(BookingStatus.CONFIRMED) // Ensure the status is set
                .build();

        // Assuming your BookingService has an 'update' method
        Booking result = iBookingService.update(updated);
        assertNotNull(result); // Ensure result is not null
        assertEquals(1500.0f, result.getTotalCost());
        assertEquals(BookingStatus.CONFIRMED, result.getStatus());
    }

    @Test
    @Order(4)
    void testGetAllBookings() {
        // Assuming your BookingService has a 'getAll' method
        List<Booking> all = iBookingService.getAll();
        assertFalse(all.isEmpty());
        // Optional: More specific assertion, e.g., check if the 'booking' created in testCreate is present
        // assertTrue(all.stream().anyMatch(b -> b.getBookingId().equals(BOOKING_ID)));
    }

    @Test
    @Order(5)
    void testDeleteBooking() {
        // Assuming your BookingService has a 'delete' method
        iBookingService.delete(BOOKING_ID);

        // Verify deletion by attempting to read; this is a more robust test
        Optional<Booking> deletedBooking = iBookingService.read(BOOKING_ID);
        assertFalse(deletedBooking.isPresent()); // Assert that it's no longer found
    }
}