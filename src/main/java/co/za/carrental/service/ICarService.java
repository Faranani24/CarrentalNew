package co.za.carrental.service;

import co.za.carrental.domain.CarService; // Import the domain object CarService
import java.util.List;
import java.util.Optional;

public interface ICarService {
    CarService create(CarService carService);
    Optional<CarService> read(String id);
    CarService update(CarService carService);
    void delete(String id);
    List<CarService> getAll();

}

=======

