package co.za.carrental.factory;

import co.za.carrental.domain.Booking;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookingFactoryTest {

    @Test
    void createBooking() {
        String bookingId = "B001";
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 86400000); // +1 day
        Float totalCost = 1500.0f;
        // Replace Enum with actual status type if available, using null for now
        Enum status = null;

        Booking booking = BookingFactory.createBooking(
                bookingId, startDate, endDate, totalCost, status
        );

        assertNotNull(booking);
        assertEquals(bookingId, booking.getBookingId());
        assertEquals(totalCost, booking.getTotalCost());
        assertEquals(startDate, booking.getStartDate());
        assertEquals(endDate, booking.getEndDate());
    }
}
