package co.za.carrental.service;

import co.za.carrental.api.dto.BookingRequest;
import co.za.carrental.domain.*;
import co.za.carrental.factory.BookingFactory;
import co.za.carrental.repository.CarRepository;
import co.za.carrental.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookingServiceTest {

    @Autowired
    private IBookingService bookingService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void seedCars() {
        persistCarIfAbsent("car-1", new BigDecimal("750.00"));
        persistCarIfAbsent("car-a", new BigDecimal("500.00"));
        persistCarIfAbsent("car-b", new BigDecimal("600.00"));
        persistCarIfAbsent("car-x", new BigDecimal("400.00"));
        persistCarIfAbsent("car-z", new BigDecimal("550.00"));
    }

    private void persistCarIfAbsent(String carId, BigDecimal dailyRate) {
        if (carRepository.findByCarId(carId).isEmpty()) {
            Car c = new Car();
            c.setCarId(carId);
            c.setDailyRate(dailyRate);
            carRepository.save(c);
        }
    }

    @Test
    void createFromRequest_basicPersistence() {
        BookingRequest req = new BookingRequest();
        req.setFullName("Alice Tester");
        req.setEmail("alice.simple@example.com");
        req.setPhone("0100000001");
        req.setCarId("car-1");
        req.setFrom("2025-05-10");
        req.setTo("2025-05-12");

        Booking booking = bookingService.createFromRequest(req);

        assertNotNull(booking.getBookingId());
        assertEquals("alice.simple@example.com", booking.getCustomer().getEmail());
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        assertNotNull(booking.getCar());
        assertEquals("car-1", booking.getCar().getCarId());
        assertTrue(booking.getTotalCost() > 0);
    }

    @Test
    void save_read_update_delete_flow() {
        Customer c = persistCustomer("crud@example.com");
        Booking b = BookingFactory.buildBooking(
                new Date(),
                new Date(System.currentTimeMillis() + 86_400_000),
                100f,
                BookingStatus.PENDING,
                c
        );
        b.setCar(carRepository.findByCarId("car-x").orElseThrow());

        Booking saved = bookingService.save(b);
        assertNotNull(saved.getBookingId());

        Optional<Booking> found = bookingService.findById(saved.getBookingId());
        assertTrue(found.isPresent());

        found.get().setStatus(BookingStatus.CONFIRMED);
        Booking updated = bookingService.update(found.get());
        assertEquals(BookingStatus.CONFIRMED, updated.getStatus());

        bookingService.deleteById(updated.getBookingId());
        assertTrue(bookingService.findById(updated.getBookingId()).isEmpty());
    }

    @Test
    void createFromRequest_reusesCustomerByEmail() {
        String email = "reuse.simple@example.com";

        BookingRequest first = new BookingRequest();
        first.setFullName("First User");
        first.setEmail(email);
        first.setPhone("0100000002");
        first.setCarId("car-a");
        first.setFrom("2025-06-01");
        first.setTo("2025-06-02");
        Booking b1 = bookingService.createFromRequest(first);
        String customerId = b1.getCustomer().getCustomerId();

        BookingRequest second = new BookingRequest();
        second.setFullName("Second NameIgnored");
        second.setEmail(email);
        second.setPhone("0100000003");
        second.setCarId("car-b");
        second.setFrom("2025-06-05");
        second.setTo("2025-06-05");
        Booking b2 = bookingService.createFromRequest(second);

        assertEquals(customerId, b2.getCustomer().getCustomerId());
        assertNotNull(b2.getCar());
        assertEquals("car-b", b2.getCar().getCarId());
    }

    @Test
    void update_nonExistingActsAsUpsert() {
        Customer c = persistCustomer("detached.simple@example.com");
        Booking detached = BookingFactory.buildBooking(
                new Date(),
                new Date(System.currentTimeMillis() + 86_400_000),
                80f,
                BookingStatus.PENDING,
                c
        );
        detached.setBookingId(UUID.randomUUID().toString());
        detached.setCar(carRepository.findByCarId("car-z").orElseThrow());

        Booking saved = bookingService.update(detached);
        assertNotNull(saved.getBookingId());
        assertTrue(bookingService.findById(saved.getBookingId()).isPresent());
    }

    private Customer persistCustomer(String email) {
        Customer c = new Customer.Builder()
                .setCustomerId(UUID.randomUUID().toString())
                .setFirstName("Test")
                .setLastName("User")
                .setEmail(email)
                .setPhone("0100000999")
                .build();
        return customerRepository.save(c);
    }
}