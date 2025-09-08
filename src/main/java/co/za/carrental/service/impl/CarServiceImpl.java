package co.za.carrental.service.impl;

import co.za.carrental.domain.Car;
import co.za.carrental.repository.CarRepository;
import co.za.carrental.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements ICarService {

    private final CarRepository carRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Optional<Car> findByCarId(String carId) {
        return carRepository.findByCarId(carId);
    }

    @Override
    public Optional<Car> findById(String carId) {
        // Implement logic to find by ID if you want both
        return Optional.empty();
    }

    @Override
    public Car update(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void deleteByCarId(String carId) {
        carRepository.deleteByCarId(carId);
    }

    @Override
    public void deleteById(String carId) {
        // Implement logic to delete by ID if you want both
    }


    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }
}