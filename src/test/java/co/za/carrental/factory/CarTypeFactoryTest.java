package co.za.carrental.factory;

import co.za.carrental.domain.CarType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarTypeFactoryTest {
    @Test
    void createCarType() {
        CarType carType = CarTypeFactory.createCarType("SEDAN_001", 4, 300.0f, 30.0f);


        assertNotNull(carType);
        assertEquals("SEDAN_001", carType.getTypeId());
        assertEquals(4, carType.getSeatingCapacity());
        assertEquals(300.0f, carType.getDailyRate());
        assertEquals(30.0f, carType.getLateFeePerHour());
    }

}