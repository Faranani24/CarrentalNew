package co.za.carrental.service.impl;

import co.za.carrental.domain.Car;
import co.za.carrental.domain.CarType;
import co.za.carrental.domain.Status;
import co.za.carrental.factory.CarFactory;
import co.za.carrental.factory.CarTypeFactory;
import co.za.carrental.repository.CarTypeRepository;
import co.za.carrental.service.ICarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CarServiceImplTest {

    @Autowired
    private ICarService carService;

    @Autowired
    private CarTypeRepository carTypeRepository;

    private CarType testCarType;
    private Car testCar;


    @BeforeEach
    void setUp() {
        testCarType = CarTypeFactory.createCarType(
                "CT001", "Sedan", 500.0f, 50.0f, 5
        );
        testCarType = carTypeRepository.save(testCarType);
        assertNotNull(testCarType.getTypeId());

        testCar = CarFactory.createCar(
                "ABC123GP", "Toyota", "Corolla", 2020, Status.AVAILABLE, testCarType, BigDecimal.valueOf(100.00)
        );
        testCar = carService.save(testCar);
        assertNotNull(testCar.getCarId());
    }

    @Test
    void read_shouldReturnCarById() {
        Optional<Car> found = carService.findById(testCar.getCarId());
        assertTrue(found.isPresent());
        assertEquals(testCar.getCarId(), found.get().getCarId());
    }

    @Test
    void create_shouldSaveCar() {
        Car newCar = CarFactory.createCar(
                "XYZ789GP", "Honda", "Civic", 2021, Status.AVAILABLE, testCarType, BigDecimal.valueOf(120.00)
        );
        Car savedCar = carService.save(newCar);
        assertNotNull(savedCar);
        assertNotNull(savedCar.getCarId());
    }

    @Test
    void update_shouldUpdateCar() {
        testCar.setMake("Updated Toyota");
        Car updatedCar = carService.update(testCar);
        assertNotNull(updatedCar);
        assertEquals("Updated Toyota", updatedCar.getMake());
    }

    @Test
    void delete_shouldRemoveCarById() {
        carService.deleteById(testCar.getCarId());
        Optional<Car> found = carService.findById(testCar.getCarId());
        assertFalse(found.isPresent());
    }
}