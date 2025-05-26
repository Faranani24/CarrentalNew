package co.za.carrental.service.impl;

import co.za.carrental.domain.Booking;
import co.za.carrental.repository.BookingRepository;
import co.za.carrental.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepository;


    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Optional<Booking> findById(String bookingId) {
        return bookingRepository.findById(bookingId);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking update(Booking booking) {
        Optional<Booking> existingBookingOpt = bookingRepository.findById(booking.getBookingId());
        if (existingBookingOpt.isPresent()) {
            Booking existingBooking = existingBookingOpt.get();
            existingBooking.setStartDate(booking.getStartDate());
            existingBooking.setEndDate(booking.getEndDate());
            existingBooking.setStatus(booking.getStatus());
            existingBooking.setTotalCost(booking.getTotalCost());
            return bookingRepository.save(existingBooking);
        } else {
            throw new IllegalArgumentException("Booking with ID " + booking.getBookingId() + " does not exist.");
        }
    }

    @Override
    public void deleteById(String bookingId) {
        if (bookingRepository.existsById(bookingId)) {
            bookingRepository.deleteById(bookingId);
        } else {
            throw new IllegalArgumentException("Booking with ID " + bookingId + " not found for deletion.");
        }
    }

    @Override
    public Booking create(Booking booking) {
        return null;
    }

    @Override
    public Optional<Booking> read(String bookingId) {
        return Optional.empty();
    }

    @Override
    public List<Booking> getAll() {
        return List.of();
    }

    @Override
    public void delete(String bookingId) {

    }
}