package co.za.carrental.service;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.Customer;
import co.za.carrental.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements IBookingService {

    private final BookingRepository repository;

    @Autowired
    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Booking create(Booking booking) {
        return repository.save(booking);
    }

    @Override
    public Optional<Customer> read(String bookingId) {
        return repository.findById(bookingId);
    }

    @Override
    public Booking update(Booking booking) {
        return null;
    }

    @Override
    public List<Booking> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String bookingId) {
        repository.deleteById(bookingId);
    }
}
