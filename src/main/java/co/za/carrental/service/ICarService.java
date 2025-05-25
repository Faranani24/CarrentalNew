package co.za.carrental.service;

import co.za.carrental.service.impl.CarService; // Import the domain object CarService
import java.util.List;
import java.util.Optional;

public interface ICarService {
    CarService create(CarService carService); // Save/create
    Optional<CarService> read(String id);     // Find by ID (ID type changed to String)
    CarService update(CarService carService); // Update
    void delete(String id);                   // Delete by ID (ID type changed to String)
    List<CarService> getAll();                // Find all
}