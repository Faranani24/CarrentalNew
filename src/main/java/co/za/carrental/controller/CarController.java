package co.za.carrental.controller;

import co.za.carrental.domain.Car;
import co.za.carrental.service.ICarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;
import java.util.List;

@CrossOrigin(origins = {"${app.cors.allowed-origins}", "http://localhost:5173"})
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final ICarService carService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CarController(ICarService carService, ObjectMapper objectMapper) {
        this.carService = carService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Car> create(@RequestPart("car") String carJson, @RequestPart("image") MultipartFile imageFile) {
        try {
            Car car = objectMapper.readValue(carJson, Car.class);
            car.setImage(imageFile.getBytes());
            Car created = carService.save(car);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid car data or image file.", e);
        }
    }

    @GetMapping("/{carId}")
    public ResponseEntity<Car> read(@PathVariable String carId) {
        Optional<Car> car = carService.findByCarId(carId);
        return car.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // New GET endpoint to retrieve a car's image by its ID
    @GetMapping(value = "/{carId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String carId) {
        Optional<Car> car = carService.findByCarId(carId);
        return car.map(c -> {
            if (c.getImage() != null) {
                return ResponseEntity.ok().body(c.getImage());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found for carId: " + carId);
            }
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found with carId: " + carId));
    }

    @PutMapping("/{carId}")
    public ResponseEntity<Car> update(@PathVariable String carId, @RequestBody Car car) {
        car.setCarId(carId);
        Car updated = carService.update(car);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> delete(@PathVariable String carId) {
        carService.deleteByCarId(carId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAll() {
        // The image field is ignored on serialization, so this returns a list of cars without the image data.
        List<Car> cars = carService.getAll();
        return ResponseEntity.ok(cars);
    }
}
