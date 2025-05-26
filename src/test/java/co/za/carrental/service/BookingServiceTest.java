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
    private IBookingService iBookingService;

    @Autowired
    private CustomerService customerService;

    private static final String BOOKING_ID = "b001";
    private static final String CUSTOMER_ID = "cust001";

    private static Booking booking;

    @BeforeAll
    static void setup() {

    }

    @Test
    @Order(1)
    void testCreateCustomerAndBooking(Car car) {
        Customer customer = new Customer.Builder(
                CUSTOMER_ID, "Alice", "Smith", "alice@example.com")
                .password("pass123")
                .phoneNumber("0123456789")
                .licenseNumber("LIC123")
                .paymentMethods(List.of("Card", "Cash"))
                .build();


        customerService.save(customer);

        booking = BookingFactory.createBooking(
                BOOKING_ID,
                new Date(),
                new Date(System.currentTimeMillis() + 86400000), // 1 day later
                1200.0f,
                BookingStatus.CONFIRMED,
                customer, car);


        Booking created = iBookingService.create(booking);
        assertNotNull(created);
        assertEquals(BOOKING_ID, created.getBookingId());
    }

    @Test
    @Order(2)
    void testReadBooking() {
        Optional<Booking> read = iBookingService.read(BOOKING_ID);
        assertTrue(read.isPresent());
        assertEquals(BOOKING_ID, read.get().getBookingId());
    }

    @Test
    @Order(3)
    void testUpdateBooking() {
        Booking updated = new Booking.Builder()
                .setBookingId(BOOKING_ID)
                .setStartDate(booking.getStartDate())
                .setEndDate(new Date(System.currentTimeMillis() + 172800000))
                .setTotalCost(1500.0f)
                .setStatus(BookingStatus.CONFIRMED)
                .build();


        Booking result = iBookingService.update(updated);
        assertNotNull(result);
        assertEquals(1500.0f, result.getTotalCost());
        assertEquals(BookingStatus.CONFIRMED, result.getStatus());
    }

    @Test
    @Order(4)
    void testGetAllBookings() {
        List<Booking> all = iBookingService.getAll();
        assertFalse(all.isEmpty());

    }

    @Test
    @Order(5)
    void testDeleteBooking() {
        iBookingService.delete(BOOKING_ID);
        Optional<Booking> deletedBooking = iBookingService.read(BOOKING_ID);
        assertFalse(deletedBooking.isPresent());
    }
}