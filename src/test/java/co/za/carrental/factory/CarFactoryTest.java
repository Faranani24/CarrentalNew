// src/test/java/co/za/carrental/factory/CarFactoryTest.java
package co.za.carrental.factory;

import co.za.carrental.domain.Car;
import co.za.carrental.domain.CarType;
import co.za.carrental.domain.Status;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CarFactoryTest {

    private CarType createTestCarType() {
        return CarType.builder()
                .typeId("T001")
                .name("SUV")
                .dailyRate(500.0f)
                .lateFeePerHour(50.0f)
                .seatingCapacity(5)
                .build();
    }

    @Test
    void buildCar_shouldReturnCarWithValidProperties() {
        CarType carType = createTestCarType();

        Car car = CarFactory.buildCar(
                "C001", "Toyota", "Corolla", 2020, "ABC123", carType, BigDecimal.valueOf(100.00)
        );

        assertNotNull(car);
        assertEquals("C001", car.getCarId());
        assertEquals("Toyota", car.getMake());
        assertEquals("Corolla", car.getModel());
        assertEquals(2020, car.getYear());
        assertEquals("ABC123", car.getLicensePlate());
        assertNotNull(car.getCarType());
        assertEquals("T001", car.getCarType().getTypeId());
        assertEquals(BigDecimal.valueOf(100.00), car.getDailyRate());
    }

    @Test
    void createCar_shouldReturnCarWithValidProperties() {
        CarType carType = createTestCarType();

        Car car = CarFactory.createCar(
                "ABC123", "Toyota", "Corolla", 2020, Status.AVAILABLE, carType, BigDecimal.valueOf(120.00)
        );

        assertNotNull(car);
        assertNotNull(car.getCarId());
        assertEquals("ABC123", car.getLicensePlate());
        assertEquals("Toyota", car.getMake());
        assertEquals("Corolla", car.getModel());
        assertEquals(2020, car.getYear());
        assertEquals(Status.AVAILABLE, car.getStatus()); // fixed
        // alternatively: assertEquals("AVAILABLE", car.getStatus().name());
        assertNotNull(car.getCarType());
        assertEquals("SUV", car.getCarType().getName());
        assertEquals(BigDecimal.valueOf(120.00), car.getDailyRate());
    }
}