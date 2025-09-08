package co.za.carrental.service;

import co.za.carrental.domain.Car;
import java.util.List;
import java.util.Optional;

public interface ICarService {
    Car save(Car car);
    Optional<Car> findByCarId(String carId);
    Optional<Car> findById(String carId); // Add this if you want both
    Car update(Car car);
    void deleteByCarId(String carId);
    void deleteById(String carId); // Add this if you want both
    List<Car> getAll();
}
