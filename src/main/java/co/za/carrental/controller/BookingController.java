package co.za.carrental.controller;

import co.za.carrental.api.dto.BookingRequest;
import co.za.carrental.api.dto.BookingResponse;
import co.za.carrental.domain.Booking;
import co.za.carrental.domain.BookingStatus;
import co.za.carrental.service.IBookingService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final IBookingService bookingService;

    public BookingController(IBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> create(@RequestBody BookingRequest request) {
        Booking created = bookingService.createFromRequest(request);

        BookingResponse dto = new BookingResponse(
                created.getBookingId(),
                created.getStartDate() != null ? created.getStartDate().toString() : null,
                created.getEndDate() != null ? created.getEndDate().toString() : null,
                created.getTotalCost(),
                created.getStatus() != null ? created.getStatus().name() : null
        );

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> read(@PathVariable String bookingId) {
        return bookingService.read(bookingId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<Booking> update(@PathVariable String bookingId,
                                          @RequestBody Booking incoming) {
        if (incoming.getBookingId() == null) {
            incoming.setBookingId(bookingId);
        } else if (!bookingId.equals(incoming.getBookingId())) {
            return ResponseEntity.badRequest().build();
        }
        if (bookingService.read(bookingId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Booking saved = bookingService.update(incoming);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> delete(@PathVariable String bookingId) {
        if (bookingService.read(bookingId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bookingService.delete(bookingId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable String bookingId) {
        try {
            Optional<Booking> bookingOpt = bookingService.read(bookingId);

            if (bookingOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Booking booking = bookingOpt.get();

            // Check if booking can be cancelled
            if (booking.getStatus() == BookingStatus.CANCELLED) {
                return ResponseEntity.badRequest().body("Booking is already cancelled");
            }

            if (booking.getStatus() == BookingStatus.COMPLETED) {
                return ResponseEntity.badRequest().body("Cannot cancel a completed booking");
            }

            // Update booking status to CANCELLED
            booking.setStatus(BookingStatus.CANCELLED);
            Booking updatedBooking = bookingService.update(booking);

            return ResponseEntity.ok(new BookingResponse(
                    updatedBooking.getBookingId(),
                    updatedBooking.getStartDate() != null ? updatedBooking.getStartDate().toString() : null,
                    updatedBooking.getEndDate() != null ? updatedBooking.getEndDate().toString() : null,
                    updatedBooking.getTotalCost(),
                    updatedBooking.getStatus() != null ? updatedBooking.getStatus().name() : null
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error cancelling booking: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAll() {
        return ResponseEntity.ok(bookingService.getAll());
    }
}