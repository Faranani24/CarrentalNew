package co.za.carrental.service;

import co.za.carrental.api.dto.BookingRequest;
import co.za.carrental.domain.Booking;

import java.util.List;
import java.util.Optional;

/**
 * Booking service API. Includes both direct CRUD (used by service tests)
 * and request-based creation (used by controller tests).
 */
public interface IBookingService {

    // Direct persistence
    Booking save(Booking booking);
    Optional<Booking> findById(String bookingId);
    Booking update(Booking booking);
    void deleteById(String bookingId);

    // Controller-oriented aliases
    Optional<Booking> read(String bookingId);      // alias of findById
    void delete(String bookingId);                 // alias of deleteById
    List<Booking> getAll();

    // Get bookings by customer email
    List<Booking> getBookingsByCustomerEmail(String email);

    // Form/request creation
    Booking createFromRequest(BookingRequest request);
}