package co.za.carrental.service.impl;

import co.za.carrental.domain.Booking;
import co.za.carrental.domain.BookingStatus;
import co.za.carrental.domain.Customer;
import co.za.carrental.api.dto.BookingRequest;
import co.za.carrental.repository.BookingRepository;
import co.za.carrental.repository.CustomerRepository;
import co.za.carrental.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BookingServiceImpl implements IBookingService {

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;

    // Mock car data for cost calculation (replace with actual car service when available)
    private static final double BMW_DAILY_RATE = 150.00;
    private static final double TOYOTA_DAILY_RATE = 75.00;
    private static final double HONDA_DAILY_RATE = 85.00;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, CustomerRepository customerRepository) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
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
        if (booking.getBookingId() == null) {
            booking.setBookingId(UUID.randomUUID().toString());
        }
        if (booking.getStatus() == null) {
            booking.setStatus(BookingStatus.CONFIRMED);
        }
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
        // Custom logic here if needed
    }

    /**
     * Create booking from Vue form request
     */
    public Booking createFromRequest(BookingRequest request) {
        try {
            // Find or create customer
            Customer customer = findOrCreateCustomer(request.getFullName(), request.getEmail(), request.getPhone());

            // Parse dates
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(request.getFrom());
            Date endDate = dateFormat.parse(request.getTo());

            // Calculate total cost
            Float totalCost = calculateTotalCost(request.getCarId(), startDate, endDate);

            // Create booking using builder
            Booking booking = new Booking.Builder()
                    .setBookingId(UUID.randomUUID().toString())
                    .setStartDate(startDate)
                    .setEndDate(endDate)
                    .setTotalCost(totalCost)
                    .setStatus(BookingStatus.CONFIRMED)
                    .setCustomer(customer)
                    .setVehicleId(request.getCarId())
                    .build();

            return bookingRepository.save(booking);

        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format", e);
        }
    }

    private Customer findOrCreateCustomer(String fullName, String email, String phone) {
        // Try to find existing customer by email
        Optional<Customer> existingCustomer = customerRepository.findByEmail(email);

        if (existingCustomer.isPresent()) {
            return existingCustomer.get();
        }

        // Create new customer if not found
        // Split fullName into firstName and lastName
        String[] nameParts = fullName.trim().split("\\s+", 2);
        String firstName = nameParts[0];
        String lastName = nameParts.length > 1 ? nameParts[1] : "";

        Customer newCustomer = new Customer.Builder()
                .setCustomerId(UUID.randomUUID().toString())
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setPhone(phone)
                .build();

        return customerRepository.save(newCustomer);
    }

    private Float calculateTotalCost(String carId, Date startDate, Date endDate) {
        // Calculate duration in days
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        long diffInDays = diffInMillies / (24 * 60 * 60 * 1000) + 1; // +1 to include the last day

        // Get daily rate based on car ID (replace with actual car service lookup)
        double dailyRate = getDailyRateByCarId(carId);

        return (float) (dailyRate * diffInDays);
    }

    private double getDailyRateByCarId(String carId) {
        // This is temporary - replace with actual car service when available
        switch (carId) {
            case "1": return BMW_DAILY_RATE;
            case "2": return TOYOTA_DAILY_RATE;
            case "3": return HONDA_DAILY_RATE;
            default: return 100.00; // Default rate
        }
    }
}