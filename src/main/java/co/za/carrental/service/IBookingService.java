package co.za.carrental.service;

import co.za.carrental.domain.Booking;
import co.za.carrental.api.dto.BookingRequest;

import java.util.List;
import java.util.Optional;

public interface IBookingService extends IService<Booking, String> {
    void customBookingLogic();

    Booking save(Booking booking);

    Optional<Booking> findById(String bookingId);

    List<Booking> findAll();

    void deleteById(String bookingId);
    // Add this to your IBookingService interface
    Booking createFromRequest(BookingRequest request);
    // NEW: Method to create booking from Vue form data
    // Note: This will be implemented in BookingServiceImpl
    // Booking createFromRequest(BookingRequest request);
}