package co.za.carrental.controller;

import co.za.carrental.domain.Car;
import co.za.carrental.domain.CarType;
import co.za.carrental.domain.Status;
import co.za.carrental.factory.CarFactory;
import co.za.carrental.repository.CarRepository;
import co.za.carrental.repository.CarTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarControllerTest {

    @Autowired
    private CarController carController;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarTypeRepository carTypeRepository;

    private Car testCar;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        carTypeRepository.deleteAll();

        CarType carType = CarType.builder()
                .typeId("T001")
                .name("SUV")
                .dailyRate(500.0f)
                .lateFeePerHour(50.0f)
                .seatingCapacity(5)
                .build();

        carType = carTypeRepository.save(carType);

        testCar = CarFactory.createCar(
                "12345", "Toyota", "Corolla", 2023, Status.AVAILABLE, carType, BigDecimal.valueOf(100.00)
        );
        carRepository.save(testCar);
    }

    @Test
    void create_shouldCreateCar() {
        CarType carType = CarType.builder()
                .typeId("T002")
                .name("Sedan")
                .dailyRate(400.0f)
                .lateFeePerHour(40.0f)
                .seatingCapacity(4)
                .build();

        carType = carTypeRepository.save(carType);

        Car newCar = CarFactory.createCar(
                "67890", "Honda", "Civic", 2022, Status.AVAILABLE, carType, BigDecimal.valueOf(120.00)
        );
        ResponseEntity<Car> response = carController.create(newCar);

        assertNotNull(response.getBody());
        assertEquals("Honda", response.getBody().getMake());
        assertEquals(BigDecimal.valueOf(120.00), response.getBody().getDailyRate());
        assertEquals(2, carRepository.count());
    }

    @Test
    void getAll_shouldReturnAllCars() {
        ResponseEntity<List<Car>> response = carController.getAll();

        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void read_shouldReturnCarById() {
        ResponseEntity<Car> response = carController.read(testCar.getCarId());

        assertNotNull(response.getBody(), "Car should not be null");
        assertEquals("Toyota", response.getBody().getMake());
        assertTrue(BigDecimal.valueOf(100.00).compareTo(response.getBody().getDailyRate()) == 0, "Daily rate should match");
    }

    @Test
    void update_shouldUpdateCar() {
        testCar.setMake("Updated Toyota");
        testCar.setDailyRate(BigDecimal.valueOf(150.00));
        ResponseEntity<Car> response = carController.update(testCar.getCarId(), testCar);

        assertNotNull(response.getBody());
        assertEquals("Updated Toyota", response.getBody().getMake());
        assertEquals(BigDecimal.valueOf(150.00), response.getBody().getDailyRate());
    }

    @Test
    void delete_shouldDeleteCar() {
        // Delete the car
        carController.delete(testCar.getCarId());

        // Verify the car is deleted
        assertEquals(0, carRepository.count(), "Car count should be 0 after deletion");
    }
}