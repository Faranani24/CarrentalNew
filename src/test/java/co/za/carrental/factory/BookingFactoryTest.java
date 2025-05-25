/*BookingFactoryTest.java
BookingFactoryTest
Lance Anthony Franks
17 May 2025
 */

package co.za.carrental.factory;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.BookingStatus; // Make sure this import is present
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

        // --- FIX IS HERE ---
        // Provide a concrete, non-null BookingStatus enum value
        BookingStatus status = BookingStatus.PENDING; // Or CONFIRMED, CANCELLED, COMPLETED, depending on your enum values

        Booking booking = BookingFactory.createBooking(
                bookingId, startDate, endDate, totalCost, status // Pass the concrete status
        );

        assertNotNull(booking);
        assertEquals(bookingId, booking.getBookingId());
        assertEquals(totalCost, booking.getTotalCost());
        assertEquals(startDate, booking.getStartDate());
        assertEquals(endDate, booking.getEndDate());
        assertEquals(BookingStatus.PENDING, booking.getStatus()); // Add assertion for status
    }
}