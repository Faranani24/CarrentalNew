package co.za.carrental.factory;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.BookingStatus;
import co.za.carrental.domain.Car;
import co.za.carrental.domain.Customer;

import java.time.LocalDate;
import java.util.UUID;

public class BookingFactory {

    public static Booking buildBooking(
            LocalDate startDate,
            LocalDate endDate,
            float totalCost,
            BookingStatus status,
            Customer customer,
            Car car) { // Added Car parameter

        if (startDate == null) throw new IllegalArgumentException("Start date required");
        if (endDate == null) throw new IllegalArgumentException("End date required");
        if (endDate.isBefore(startDate)) throw new IllegalArgumentException("End date must be after start date"); // Corrected to use LocalDate method
        if (totalCost <= 0f) throw new IllegalArgumentException("Total cost must be > 0");
        if (status == null) throw new IllegalArgumentException("Status required");
        if (customer == null) throw new IllegalArgumentException("Customer required");
        if (car == null) throw new IllegalArgumentException("Car required"); // Added check for Car

        return new Booking.Builder()
                .setBookingId("BOOK-" + UUID.randomUUID())
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setTotalCost(totalCost)
                .setStatus(status)
                .setCustomer(customer)
                .setCar(car) // Added car to the builder
                .build();
    }
}