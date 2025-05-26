package co.za.carrental.service;

import co.za.carrental.domain.Car;
import co.za.carrental.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {



    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car create(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Optional<Car> read(String carId) {
        return carRepository.findById(carId);
    }

    @Override
    public Car update(Car car) {
        if (carRepository.existsById(car.getCarId())) {
            return carRepository.save(car);
        }
        return null;
    }

    @Override
    public void delete(String carId) {
        carRepository.deleteById(carId);
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }
}
