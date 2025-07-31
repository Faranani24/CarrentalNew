package co.za.carrental.controller;

import co.za.carrental.domain.CarType;
import co.za.carrental.service.ICarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cartypes")
public class CarTypeController {

    private final ICarTypeService carTypeService;

    @Autowired
    public CarTypeController(ICarTypeService carTypeService) {
        this.carTypeService = carTypeService;
    }

    @PostMapping
    public ResponseEntity<CarType> create(@RequestBody CarType carType) {
        CarType created = carTypeService.create(carType);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarType> read(@PathVariable String id) {
        Optional<CarType> carType = carTypeService.read(id);
        return carType.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarType> update(@PathVariable String id, @RequestBody CarType carType) {
        carType.setTypeId(id);
        CarType updated = carTypeService.update(carType);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        carTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CarType>> getAll() {
        List<CarType> carTypes = carTypeService.getAll();
        return ResponseEntity.ok(carTypes);
    }
}