package co.za.carrental.controller;

import co.za.carrental.domain.CarType;
import co.za.carrental.repository.CarTypeRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/cartypes")
public class CarTypeController {

    private final CarTypeRepository repository;

    public CarTypeController(CarTypeRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public List<CarType> all() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public CarType one(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new CarTypeNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<CarType> create(@Valid @RequestBody CarType carType) {
        if (repository.existsById(carType.getTypeId())) {
            throw new IllegalArgumentException("CarType already exists: " + carType.getTypeId());
        }
        CarType saved = repository.save(carType);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getTypeId())
                .toUri();
        return ResponseEntity.created(location).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarType> update(@PathVariable String id, @Valid @RequestBody CarType carType) {
        if (!id.equals(carType.getTypeId())) {
            throw new IllegalArgumentException("Path id and body id mismatch");
        }
        if (!repository.existsById(id)) {
            throw new CarTypeNotFoundException(id);
        }
        CarType saved = repository.save(carType);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!repository.existsById(id)) {
            throw new CarTypeNotFoundException(id);
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<CarType> read(String id) {
        return ResponseEntity.ok(one(id));
    }

    public ResponseEntity<List<CarType>> getAll() {
        return ResponseEntity.ok(all());
    }
}