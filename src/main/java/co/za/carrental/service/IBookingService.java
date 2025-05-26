package co.za.carrental.service;

import co.za.carrental.domain.Booking;
import java.util.List;
import java.util.Optional;

public interface IBookingService {
    Booking save(Booking booking);
    Optional<Booking> findById(String bookingId);
    List<Booking> findAll();
    Booking update(Booking booking);
    void deleteById(String bookingId);

    Booking create(Booking booking);

    Optional<Booking> read(String bookingId);

    List<Booking> getAll();

    void delete(String bookingId);

}