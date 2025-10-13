package co.za.carrental.service;

import co.za.carrental.domain.Car;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICarService {
    Car save(Car car);
    Optional<Car> findByCarId(String carId);
    Car update(Car car);
    void deleteByCarId(String carId);
    List<Car> getAll();

    // Use LocalDate instead of String
    List<Car> getAvailableCars(LocalDate startDate, LocalDate endDate);
}
