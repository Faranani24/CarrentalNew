package co.za.carrental.service;

import co.za.carrental.domain.Car;
import java.util.List;
import java.util.Optional;

public interface CarService {
    Car create(Car car);
    Optional<Car> read(String carId);
    Car update(Car car);
    void delete(String carId);
    List<Car> findAll();


}
