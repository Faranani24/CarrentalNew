package co.za.carrental.factory;

import co.za.carrental.domain.CarType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTypeFactoryTest {

    @Test
    void createCarType_shouldCreateCorrectCarType() {
        CarType carType = CarTypeFactory.createCarType(
                "CT001", "Compact", 250.0f, 50.0f, 5
        );

        assertNotNull(carType);
        assertEquals("CT001", carType.getTypeId());
        assertEquals("Compact", carType.getName());
        assertEquals(250.0f, carType.getDailyRate());
        assertEquals(50.0f, carType.getLateFeePerHour());
        assertEquals(5, carType.getSeatingCapacity());
    }

    @Test
    void createDefaultCarType_shouldReturnPredefinedCarType() {
        CarType carType = CarTypeFactory.createDefaultCarType();

        assertNotNull(carType);
        assertEquals("default-type-id", carType.getTypeId());
        assertEquals("Default Car Type", carType.getName());
        assertEquals(100.0f, carType.getDailyRate());
        assertEquals(20.0f, carType.getLateFeePerHour());
        assertEquals(4, carType.getSeatingCapacity());
    }
}