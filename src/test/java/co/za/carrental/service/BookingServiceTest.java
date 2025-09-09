package co.za.carrental.service;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.BookingStatus;
import co.za.carrental.domain.Customer; // Import Customer
// import co.za.carrental.domain.Vehicle; // If you add Vehicle
import co.za.carrental.factory.BookingFactory;
import co.za.carrental.factory.CustomerFactory; // Import CustomerFactory
// import co.za.carrental.factory.VehicleFactory; // If you add VehicleFactory
import co.za.carrental.repository.CustomerRepository; // Import CustomerRepository
// import co.za.carrental.repository.VehicleRepository; // If you add VehicleRepository

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List; // For paymentMethods
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // Ensures clean transaction for each test method
class BookingServiceTest {

    @Autowired
    private IBookingService bookingService;

    @Autowired
    private CustomerRepository customerRepository; // Inject CustomerRepository

    // @Autowired
    // private VehicleRepository vehicleRepository; // Inject VehicleRepository, if needed

    private Customer testCustomer;
    // private Vehicle testVehicle; // If you need a test vehicle


    @BeforeEach // This method runs before EACH test method
    void setUp() {
        // 1. Create and Save a Customer first
        testCustomer = CustomerFactory.buildCustomer(
                "Test",
                "Customer",
                "test.customer@example.com",
                "password123",
                "0123456789",
                "LICENSE123",
                List.of("Credit Card") // Provide some payment methods
        );
        testCustomer = customerRepository.save(testCustomer); // Persist the customer
        assertNotNull(testCustomer.getCustomerId(), "Test Customer ID should not be null after saving");

        // 2. If you also need a Vehicle (uncomment if applicable)
        /*
        testVehicle = VehicleFactory.buildVehicle(
            "ABC 123 GP", // licensePlate
            "Make",        // make
            "Model",       // model
            2020,          // year
            "Sedan",       // type
            150000.0f,     // mileage
            true,          // available
            100.0f         // rentalRate
        );
        testVehicle = vehicleRepository.save(testVehicle); // Persist the vehicle
        assertNotNull(testVehicle.getVehicleId(), "Test Vehicle ID should not be null after saving");
        */
    }

    @Test
    void saveAndFindById_shouldPersistAndRetrieveBooking() {
        Booking booking = BookingFactory.buildBooking(
                new Date(),
                new Date(System.currentTimeMillis() + 86400000),
                1000.0f,
                BookingStatus.CONFIRMED,
                testCustomer // Pass the persisted testCustomer
                // , testVehicle // Pass the persisted testVehicle, if applicable
        );
        Booking saved = bookingService.save(booking);
        assertNotNull(saved, "Saved booking should not be null");
        assertNotNull(saved.getBookingId(), "Saved Booking ID should not be null");

        Optional<Booking> found = bookingService.findById(saved.getBookingId());
        assertTrue(found.isPresent());
        assertEquals(saved.getBookingId(), found.get().getBookingId());
        assertEquals(testCustomer.getCustomerId(), found.get().getCustomer().getCustomerId()); // Verify customer relationship
        // assertEquals(testVehicle.getVehicleId(), found.get().getVehicle().getVehicleId()); // Verify vehicle, if applicable
    }

    @Test
    void update_shouldModifyBooking() {
        Booking booking = BookingFactory.buildBooking(
                new Date(),
                new Date(System.currentTimeMillis() + 86400000),
                1000.0f,
                BookingStatus.PENDING,
                testCustomer // Pass the persisted testCustomer
                // , testVehicle // Pass the persisted testVehicle, if applicable
        );
        Booking saved = bookingService.save(booking);
        assertNotNull(saved, "Saved booking should not be null for update test");
        assertNotNull(saved.getBookingId(), "Saved Booking ID should not be null for update test");

        saved.setStatus(BookingStatus.CONFIRMED);
        Booking updated = bookingService.update(saved);
        assertNotNull(updated, "Updated booking should not be null");
        assertEquals(BookingStatus.CONFIRMED, updated.getStatus());
        assertEquals(saved.getBookingId(), updated.getBookingId());
    }

    @Test
    void deleteById_shouldRemoveBooking() {
        Booking booking = BookingFactory.buildBooking(
                new Date(),
                new Date(System.currentTimeMillis() + 86400000),
                1000.0f,
                BookingStatus.PENDING,
                testCustomer // Pass the persisted testCustomer
                // , testVehicle // Pass the persisted testVehicle, if applicable
        );
        Booking saved = bookingService.save(booking);
        assertNotNull(saved, "Saved booking should not be null for delete test");
        assertNotNull(saved.getBookingId(), "Saved Booking ID should not be null for delete test");

        bookingService.deleteById(saved.getBookingId());
        Optional<Booking> found = bookingService.findById(saved.getBookingId());
        assertFalse(found.isPresent());
    }
}