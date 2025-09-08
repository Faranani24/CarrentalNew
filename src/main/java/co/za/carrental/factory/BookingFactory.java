// File: src/main/java/co/za/carrental/factory/BookingFactory.java
package co.za.carrental.factory;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.BookingStatus;
import co.za.carrental.domain.Customer;

import java.util.Date;
import java.util.UUID;

public class BookingFactory {

    public static Booking buildBooking(Date startDate, Date endDate, float totalCost, BookingStatus status, Customer customer) {
        if (startDate == null) {
            throw new IllegalArgumentException("Start date is required.");
        }
        if (endDate == null) {
            throw new IllegalArgumentException("End date is required.");
        }
        if (endDate.before(startDate)) {
            throw new IllegalArgumentException("End date must be after start date.");
        }
        if (totalCost <= 0.0f) {
            throw new IllegalArgumentException("Total cost must be greater than 0.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Booking status is required.");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer is required.");
        }

        String bookingId = "BOOK-" + UUID.randomUUID();

        return new Booking.Builder()
                .setBookingId(bookingId)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setTotalCost(totalCost)
                .setStatus(status)
                .setCustomer(customer)
                .build();
    }
}