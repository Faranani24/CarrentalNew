package co.za.carrental.factory;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.BookingStatus;
import co.za.carrental.domain.Car;      // Import Car
import co.za.carrental.domain.Customer; // Import Customer

import java.util.Date; // Or LocalDate

public class BookingFactory {

    public static Booking createBooking(
            String bookingId,
            Customer customer, // Pass Customer object
            Car car,           // Pass Car object
            Date startDate, // Or LocalDate
            Date endDate,   // Or LocalDate
            Float totalCost, // Or Double
            BookingStatus status) {

        // Use the constructor for required fields
        return new Booking.Builder(bookingId, customer, car, startDate, endDate, totalCost)
                .status(status)
                .build();

        /*
        // Or if using individual setters
        return new Booking.Builder()
                .setBookingId(bookingId)
                .setCustomer(customer)
                .setCar(car)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setTotalCost(totalCost)
                .setStatus(status)
                .build();
        */
    }
}