package co.za.carrental.factory;

import co.za.carrental.domain.Car;
import co.za.carrental.domain.CarType;
import co.za.carrental.domain.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarFactoryTest {
    @Test
    void createCar() {
        CarType suvType = CarTypeFactory.createCarType("SUV_001", 5, 500.0f, 50.0f);

        System.out.println("Creating car with type: " + suvType);  // Added output

        Car car = CarFactory.createCar("C123", "ABC123", "Toyota",
                "RAV4", 2022, suvType);

        System.out.println("Created car: " + car);  // Added output

        assertNotNull(car);
        assertEquals("C123", car.getCarId());
        assertEquals("ABC123", car.getLicensePlate());
        assertEquals("Toyota", car.getMake());
        assertEquals(2022, car.getYear());
        assertEquals(Status.AVAILABLE, car.getStatus());
        assertNotNull(car.getCarType());
        assertEquals("SUV_001", car.getCarType().getTypeId());

        System.out.println("All assertions passed!");  // Added output
    }
}