
package co.za.carrental.factory;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.BookingStatus;
import co.za.carrental.domain.Customer;
import co.za.carrental.domain.Car;
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
        BookingStatus status = BookingStatus.PENDING;

        Customer customer = new Customer.Builder("C001", "John", "Doe", "john@example.com").build();
        Car car = new Car.Builder("CAR001", "Toyota", "Corolla", 2020).build();


        Booking booking = BookingFactory.createBooking(
                bookingId, startDate, endDate, totalCost, status, customer, car
        );

        assertNotNull(booking);
        assertEquals(bookingId, booking.getBookingId());
        assertEquals(totalCost, booking.getTotalCost());
        assertEquals(startDate, booking.getStartDate());
        assertEquals(endDate, booking.getEndDate());
        assertEquals(BookingStatus.PENDING, booking.getStatus());
    }
}