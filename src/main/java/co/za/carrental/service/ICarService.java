package co.za.carrental.service;

import co.za.carrental.domain.Car;

import java.util.List;
import java.util.Optional;

public interface ICarService {
    Car save(Car car);
    Optional<Car> findByCarId(String carId);
    Optional<Car> findById(String id); // Add this method
    Car update(Car car);
    void deleteByCarId(String carId);
    void deleteById(String id); // Add this method
    List<Car> getAll();
}