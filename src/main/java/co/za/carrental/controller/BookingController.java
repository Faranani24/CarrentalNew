package co.za.carrental.controller;

import co.za.carrental.domain.Booking;
import co.za.carrental.service.BookingService;
import co.za.carrental.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final IBookingService service;

    @Autowired
    public BookingController(IBookingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Booking> create(@RequestBody Booking booking) {
        return ResponseEntity.ok(service.create(booking));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> read(@PathVariable String id) {
        return service.read(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Booking> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
