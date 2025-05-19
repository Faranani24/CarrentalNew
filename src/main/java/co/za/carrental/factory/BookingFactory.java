/*BookingFactory.java
BookingFactory
Lance Anthony Franks
17 May 2025
 */

package co.za.carrental.factory;

import co.za.carrental.domain.Booking;


import java.util.*;

public class BookingFactory {
    private static Enum status;

    public static Booking createBooking(String bookingId, Date startDate, Date endDate, Float totalCost, Enum Status) {

        return new Booking.Builder()
                .setBookingId(bookingId)
                .setStartDate(startDate)
                .setEndDate(endDate)
                .setTotalCost(totalCost)
                .setStatus(status)
                .build();
    }
}
