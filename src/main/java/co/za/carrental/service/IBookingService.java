package co.za.carrental.service;

import co.za.carrental.api.dto.BookingRequest;
import co.za.carrental.domain.Booking;

import java.util.List;
import java.util.Optional;

public interface IBookingService {


    Booking save(Booking booking);
    Optional<Booking> findById(String bookingId);
    Booking update(Booking booking);
    void deleteById(String bookingId);


    Optional<Booking> read(String bookingId);
    void delete(String bookingId);
    List<Booking> getAll();

    List<Booking> getBookingsByCustomerEmail(String email);

    Booking createFromRequest(BookingRequest request);
}