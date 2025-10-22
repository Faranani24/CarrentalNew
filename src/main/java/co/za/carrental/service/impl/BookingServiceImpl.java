package co.za.carrental.service.impl;

import co.za.carrental.api.dto.BookingRequest;
import co.za.carrental.domain.*;
import co.za.carrental.repository.BookingRepository;
import co.za.carrental.repository.CarRepository;
import co.za.carrental.repository.CustomerRepository;
import co.za.carrental.service.IBookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final CarRepository carRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              CustomerRepository customerRepository,
                              CarRepository carRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
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
    public Booking update(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteById(String bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public Optional<Booking> read(String bookingId) {
        return findById(bookingId);
    }

    @Override
    public void delete(String bookingId) {
        deleteById(bookingId);
    }

    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getBookingsByCustomerEmail(String email) {
        return List.of();
    }

    @Override
    public Booking createFromRequest(BookingRequest request) {

        LocalDate start = LocalDate.parse(request.getFrom());
        LocalDate end = LocalDate.parse(request.getTo());

        Customer customer = findOrCreateCustomer(
                request.getFullName(),
                request.getEmail(),
                request.getPhone()
        );

        Car car = carRepository.findByCarId(request.getCarId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found: " + request.getCarId()));

        Float totalCost = calculateTotalCost(car, start, end);

        Booking booking = new Booking.Builder()
                .setBookingId(UUID.randomUUID().toString())
                .setStartDate(start)
                .setEndDate(end)
                .setTotalCost(totalCost)
                .setStatus(BookingStatus.CONFIRMED)
                .setCustomer(customer)
                .setCar(car)
                .build();

        return bookingRepository.save(booking);
    }

    private Customer findOrCreateCustomer(String fullName, String email, String phone) {
        return customerRepository.findByEmail(email).orElseGet(() -> {
            String[] parts = (fullName == null ? "" : fullName.trim()).split("\\s+", 2);
            String first = parts.length > 0 ? parts[0] : "";
            String last = parts.length > 1 ? parts[1] : "";
            Customer c = new Customer.Builder()
                    .setCustomerId(UUID.randomUUID().toString())
                    .setFirstName(first)
                    .setLastName(last)
                    .setEmail(email)
                    .build();
            return customerRepository.save(c);
        });
    }

    private Float calculateTotalCost(Car car, LocalDate startDate, LocalDate endDate) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        if (days < 1) days = 1;
        BigDecimal rate = Optional.ofNullable(car.getDailyRate()).orElse(BigDecimal.ZERO);
        BigDecimal total = rate.multiply(BigDecimal.valueOf(days)).setScale(2, RoundingMode.HALF_UP);
        return total.floatValue();
    }
}