package co.za.carrental.controller;

import co.za.carrental.domain.Car;
import co.za.carrental.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final ICarService carService;

    @Autowired
    public CarController(ICarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<Car> create(@RequestBody Car car) {
        Car created = carService.save(car);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<Car> read(@PathVariable String carId) {
        Optional<Car> car = carService.findByCarId(carId);
        return car.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
        List<Car> cars = carService.getAll();
        return ResponseEntity.ok(cars);
    }
}