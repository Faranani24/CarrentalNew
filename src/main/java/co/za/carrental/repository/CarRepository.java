package co.za.carrental.repository;

import co.za.carrental.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CarRepository extends JpaRepository<Car, String> {


    Optional<Car> findByMake(String make);

    Optional<Car> findByModel(String model);

    Optional<Car> findByLicensePlate(String licensePlate);

    Optional<Car> findByYear(int year);
}


