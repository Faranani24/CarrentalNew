package co.za.carrental.service;

import co.za.carrental.domain.Booking;

import java.util.List;
import java.util.Optional;

public interface IBookingService extends IService<Booking, String> {
    void customBookingLogic();

    Booking save(Booking booking);

    Optional<Booking> findById(String bookingId);

    List<Booking> findAll();

    void deleteById(String bookingId);
}
