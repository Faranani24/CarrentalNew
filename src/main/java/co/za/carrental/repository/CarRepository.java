package co.za.carrental.repository;

import co.za.carrental.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByCarId(String carId);
    void deleteByCarId(String carId);
}