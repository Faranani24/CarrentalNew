
package co.za.carrental.factory;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.BookingStatus;
import co.za.carrental.domain.Customer;
import co.za.carrental.domain.Car;

import java.util.Date;

public class BookingFactory {

    public static Booking createBooking(String bookingId, Date startDate, Date endDate, Float totalCost,
                                        BookingStatus status, Customer customer, Car car) {
        if (bookingId == null || startDate == null || endDate == null || totalCost == null ||
                status == null || customer == null || car == null) {
            throw new IllegalArgumentException("All fields are required and cannot be null.");
        }

        return new Booking.Builder()
                .setBookingId(bookingId)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setTotalCost(totalCost)
                .setStatus(status)
                .setCustomer(customer)
                .setCar(car) // Ensure the car is set
                .build();
    }
}