package co.za.carrental.controller;

import co.za.carrental.domain.Car;
import co.za.carrental.service.ICarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final ICarService carService;
    private final ObjectMapper objectMapper;
    private static final long MAX_IMAGE_BYTES = 5 * 1024 * 1024;

    @Autowired
    public CarController(ICarService carService, ObjectMapper objectMapper) {
        this.carService = carService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Car> create(@RequestPart("car") String carJson,
                                      @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            Car car = objectMapper.readValue(carJson, Car.class);
            if (!isSafeId(car.getCarId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid carId");
            }
            if (imageFile != null && !imageFile.isEmpty()) {
                if (imageFile.getSize() > MAX_IMAGE_BYTES) {
                    throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "Image too large");
                }
                String ct = imageFile.getContentType();
                if (ct == null || !(ct.equals("image/png") || ct.equals("image/jpeg"))) {
                    throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Only PNG/JPEG allowed");
                }
                car.setImage(imageFile.getBytes());
            }
            Car created = carService.save(car);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (ResponseStatusException rse) {
            throw rse;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid car data or image file.", e);
        }
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAll() {
        List<Car> cars = carService.getAll();
        cars.forEach(c -> c.setImage(null)); // Remove image bytes
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Car>> getAvailableCars(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            if (startDate == null || endDate == null || endDate.isBefore(startDate)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date range");
            }
            List<Car> availableCars = carService.getAvailableCars(startDate, endDate);
            return ResponseEntity.ok(availableCars);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching available cars", e);
        }
    }

    @GetMapping("/{carId}")
    public ResponseEntity<Car> read(@PathVariable String carId) {
        return carService.findByCarId(carId)
                .map(c -> {
                    c.setImage(null);
                    return ResponseEntity.ok(c);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{carId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> update(@PathVariable String carId, @RequestBody Car incoming) {
        if (!isSafeId(carId)) return ResponseEntity.badRequest().build();
        Optional<Car> existingOpt = carService.findByCarId(carId);
        if (existingOpt.isEmpty()) return ResponseEntity.notFound().build();

        Car existing = existingOpt.get();
        incoming.setId(existing.getId());
        incoming.setCarId(carId);
        Car saved = carService.update(incoming);
        saved.setImage(null); // remove image
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> delete(@PathVariable String carId) {
        try {
            carService.deleteByCarId(carId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .header("X-Error", "Car cannot be deleted: related records exist")
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("X-Error", e.getMessage())
                    .build();
        }
    }

    @GetMapping("/{carId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable String carId) {
        return carService.findByCarId(carId)
                .map(c -> {
                    if (c.getImage() != null) {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.IMAGE_JPEG);
                        return new ResponseEntity<>(c.getImage(), headers, HttpStatus.OK);
                    }
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image not found");
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found"));
    }

    private boolean isSafeId(String id) {
        return id != null && id.matches("^[A-Za-z0-9_-]{1,64}$");
    }
}
