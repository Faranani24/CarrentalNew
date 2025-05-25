package co.za.carrental.factory;

import co.za.carrental.domain.CarService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarServiceFactoryTest {
    @Test
    void testCreateService(){
        CarService carService = ServiceFactory.createService("S001", "GPS", 50.0f);
        assertNotNull(carService);
        assertEquals("S001", carService.getServiceId());
        assertEquals("GPS", carService.getName());
        assertEquals(50.0f, carService.getCostPerDay());
    }
}
/*CarServiceFactoryTest.java
CarService Factory Test
Thabiso Kama
18 May 2025
 */
