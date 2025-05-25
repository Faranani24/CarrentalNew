//Faranani Khangale
// Created on 2025/05/25
//230136982
// This code is part of a car rental system,
//specifically for controllerTests

package co.za.carrental.controller;

import co.za.carrental.domain.Car;
import co.za.carrental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService service;

    @Autowired
    public CarController(CarService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Car> create(@RequestBody Car car) {
        return ResponseEntity.ok(service.create(car));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> read(@PathVariable String id) {
        return service.read(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Car> findAll() {
        return service.findAll();
    }

    @PutMapping
    public ResponseEntity<Car> update(@RequestBody Car car) {
        Car updated = service.update(car);
        return (updated != null)
                ? ResponseEntity.ok(updated)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
