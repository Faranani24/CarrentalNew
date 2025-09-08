package co.za.carrental.factory;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.BookingStatus;
import co.za.carrental.domain.Customer;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BookingFactoryTest {

    @Test
    void buildBooking_shouldCreateBookingWithGivenValues() {
        Customer dummyCustomer = new Customer.Builder()
                .setCustomerId(UUID.randomUUID().toString())
                .setFirstName("Factory")
                .setLastName("User")
                .setEmail("factory@example.com")
                .setPhone("1234567890")
                .setLicense("FCTRY123")
                .setPassword("dummyPass")
                .setPaymentMethods(List.of("Card"))
                .build();

        Date startDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + 86400000);
        float totalCost = 500.0f;
        BookingStatus status = BookingStatus.PENDING;

        Booking booking = BookingFactory.buildBooking(
                startDate,
                endDate,
                totalCost,
                status,
                dummyCustomer
        );

        assertNotNull(booking);
        assertNotNull(booking.getBookingId());
        assertEquals(startDate, booking.getStartDate());
        assertEquals(endDate, booking.getEndDate());
        assertEquals(totalCost, booking.getTotalCost());
        assertEquals(status, booking.getStatus());
        assertNotNull(booking.getCustomer());
        assertEquals(dummyCustomer.getCustomerId(), booking.getCustomer().getCustomerId());
    }

    @Test
    void buildBooking_shouldThrowExceptionForNullStartDate() {
        Customer dummyCustomer = new Customer.Builder()
                .setCustomerId(UUID.randomUUID().toString())
                .setFirstName("Test")
                .setLastName("User")
                .setEmail("test@example.com")
                .setPhone("1234567890")
                .setLicense("TEST123")
                .setPassword("pass")
                .setPaymentMethods(List.of("cash"))
                .build();

        Date endDate = new Date(System.currentTimeMillis() + 86400000);
        float totalCost = 500.0f;
        BookingStatus status = BookingStatus.PENDING;

        assertThrows(IllegalArgumentException.class, () ->
                BookingFactory.buildBooking(
                        null,
                        endDate,
                        totalCost,
                        status,
                        dummyCustomer
                )
        );
    }

    @Test
    void buildBooking_shouldThrowExceptionForEndDateBeforeStartDate() {
        Customer dummyCustomer = new Customer.Builder()
                .setCustomerId(UUID.randomUUID().toString())
                .setFirstName("Test")
                .setLastName("User")
                .setEmail("test@example.com")
                .setPhone("1234567890")
                .setLicense("TEST123")
                .setPassword("pass")
                .setPaymentMethods(List.of("cash"))
                .build();

        Date startDate = new Date(System.currentTimeMillis() + 86400000);
        Date endDate = new Date();
        float totalCost = 500.0f;
        BookingStatus status = BookingStatus.PENDING;

        assertThrows(IllegalArgumentException.class, () ->
                BookingFactory.buildBooking(
                        startDate,
                        endDate,
                        totalCost,
                        status,
                        dummyCustomer
                )
        );
    }
}
