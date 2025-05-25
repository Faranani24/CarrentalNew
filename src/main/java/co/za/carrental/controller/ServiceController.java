package co.za.carrental.controller;

import co.za.carrental.domain.CarService;
import co.za.carrental.service.impl.CarServiceImpl; // Assuming this is your service implementation
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.http.ResponseEntity; // Import ResponseEntity
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional; // Import Optional if using read/findById

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    private final CarServiceImpl carServiceImpl; // Use 'final' and inject via constructor

    @Autowired // Spring automatically injects
    public ServiceController(CarServiceImpl carServiceImpl) {
        this.carServiceImpl = carServiceImpl;
    }

    @PostMapping("/create")
    public ResponseEntity<CarService> create(@RequestBody CarService carService) {
        CarService createdService = this.carServiceImpl.create(carService);
        return new ResponseEntity<>(createdService, HttpStatus.CREATED); // Use 201 Created for new resources
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CarService>> getAllServices() {
        List<CarService> services = carServiceImpl.getAll();
        return new ResponseEntity<>(services, HttpStatus.OK); // Return 200 OK
    }

    // New: Get by ID method (Good practice for RESTful API)
    @GetMapping("/{id}") // Path variable 'id'
    public ResponseEntity<CarService> getServiceById(@PathVariable String id) { // ID is String now
        Optional<CarService> serviceOptional = carServiceImpl.read(id); // Use the 'read' method
        return serviceOptional.map(service -> new ResponseEntity<>(service, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Return 404 if not found
    }

    @PutMapping("/update/{id}") // Changed to @PutMapping for updates and included @PathVariable
    public ResponseEntity<CarService> update(@PathVariable String id, @RequestBody CarService carService) {
        // Ensure the ID in the path matches the ID in the request body for consistency
        if (!id.equals(carService.getServiceId())) {
            // It's good practice to handle mismatches
            throw new IllegalArgumentException("Service ID in path must match service ID in request body.");
        }
        try {
            CarService updatedService = carServiceImpl.update(carService);
            return new ResponseEntity<>(updatedService, HttpStatus.OK); // Return 200 OK
        } catch (IllegalArgumentException e) {
            // Catch the exception thrown by your service if the entity doesn't exist for update
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if not found
        }
    }

    @DeleteMapping("/delete/{id}") // Your original method
    public ResponseEntity<Void> delete(@PathVariable String id) { // FIX: Changed Integer to String
        carServiceImpl.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 No Content for successful deletion
    }
}