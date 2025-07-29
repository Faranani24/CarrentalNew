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
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteById(String bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public Booking create(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Optional<Booking> read(String bookingId) {
        return bookingRepository.findById(bookingId);
    }

    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @Override
    public void delete(String bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public void customBookingLogic() {

    }
}
