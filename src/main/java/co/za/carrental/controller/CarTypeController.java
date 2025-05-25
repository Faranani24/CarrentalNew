//Faranani Khangale
// Created on 2025/05/25
// 230136982
// This code is part of a car rental system,
// specifically the CarTypeController for managing car types in the system.
package co.za.carrental.controller;

import co.za.carrental.domain.CarType;
import co.za.carrental.service.CarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/car-types")
public class CarTypeController {

    private final CarTypeService service;

    @Autowired
    public CarTypeController(CarTypeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CarType> create(@RequestBody CarType carType) {
        return ResponseEntity.ok(service.create(carType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarType> read(@PathVariable String id) {
        return service.read(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<CarType> findAll() {
        return service.findAll();
    }

    @PutMapping
    public ResponseEntity<CarType> update(@RequestBody CarType carType) {
        CarType updated = service.update(carType);
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
