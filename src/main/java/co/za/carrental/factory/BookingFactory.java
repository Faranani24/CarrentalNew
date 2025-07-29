package co.za.carrental.factory;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.BookingStatus;
import co.za.carrental.domain.Customer; // Import Customer
// import co.za.carrental.domain.Vehicle; // If you add Vehicle
import java.util.Date;
import java.util.UUID;

public class BookingFactory {

    // IMPORTANT: Add Customer parameter
    // Also, if you have a non-nullable Vehicle, add that parameter too.
    public static Booking buildBooking(Date startDate, Date endDate, float totalCost, BookingStatus status, Customer customer /*, Vehicle vehicle */) {
        if (startDate == null || endDate == null || status == null || customer == null /* || vehicle == null */) {
            throw new IllegalArgumentException("One or more required fields (startDate, endDate, status, customer, [vehicle]) are null");
        }
        if (endDate.before(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        String bookingId = UUID.randomUUID().toString();
        return new Booking.Builder()
                .setBookingId(bookingId)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setTotalCost(totalCost)
                .setStatus(status)
                .setCustomer(customer) // Set the customer
                // .setVehicle(vehicle) // Set the vehicle, if applicable
                .build();
    }

    // You can keep your existing method if it's used elsewhere for partial building,
    // but for tests where customer is mandatory, you should use the new one.
    // If not, consider removing or making it private.
    public static Booking buildBooking(Date startDate, Date endDate, float totalCost, BookingStatus status) {
        // This method will create a booking with a NULL customer, so use it carefully.
        // It will fail when persisting if customer is nullable=false.
        throw new UnsupportedOperationException("This buildBooking method is deprecated for persistence. Use buildBooking(..., Customer customer) instead.");
        /*
        if (startDate == null || endDate == null || status == null) {
            throw new IllegalArgumentException("One or more required fields are null");
        }
        if (endDate.before(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
        String bookingId = UUID.randomUUID().toString();
        return new Booking.Builder()
                .setBookingId(bookingId)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setTotalCost(totalCost)
                .setStatus(status)
                .build();
         */
    }
}
