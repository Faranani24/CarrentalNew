package co.za.carrental.controller;

import co.za.carrental.domain.Booking;
import co.za.carrental.api.dto.BookingRequest;
import co.za.carrental.service.IBookingService;
import co.za.carrental.service.impl.BookingServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5177", "http://localhost:3000"})
public class BookingController {


    private final IBookingService bookingService;
    private final BookingServiceImpl bookingServiceImpl; // For the new method

    @Autowired
    public BookingController(IBookingService bookingService, BookingServiceImpl bookingServiceImpl) {
        this.bookingService = bookingService;
        this.bookingServiceImpl = bookingServiceImpl;
    }

    /**
     * NEW: Create booking from Vue form data
     */
    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequest request) {
        try {
            Booking created = bookingServiceImpl.createFromRequest(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Booking failed: " + e.getMessage());
        }
    }

    /**
     * EXISTING: Create booking from full Booking object (keep for other uses)
     */
    @PostMapping("/full")
    public ResponseEntity<Booking> create(@RequestBody Booking booking) {
        Booking created = bookingService.create(booking);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> read(@PathVariable String id) {
        Optional<Booking> booking = bookingService.read(id);
        return booking.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> update(@PathVariable String id, @RequestBody Booking booking) {
        booking.setBookingId(id);
        Booking updated = bookingService.update(booking);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAll() {
        List<Booking> bookings = bookingService.getAll();
        return ResponseEntity.ok(bookings);
    }

    /**
     * Get bookings by customer email (useful for customer lookup)
     */
    @GetMapping("/customer/{email}")
    public ResponseEntity<List<Booking>> getBookingsByCustomerEmail(@PathVariable String email) {
        // You'll need to add this method to your service if you want this functionality
        List<Booking> bookings = bookingService.getAll(); // For now, return all
        return ResponseEntity.ok(bookings);
    }
}