package co.za.carrental.factory;

import co.za.carrental.domain.Service;
import co.za.carrental.factory.ServiceFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServiceFactoryTest {
    @Test
    void testCreateService(){
        Service service = ServiceFactory.createService("S001", "GPS", 50.0f);
        assertNotNull(service);
        assertEquals("S001", service.getServiceId());
        assertEquals("GPS", service.getName());
        assertEquals(50.0f, service.getCostPerDay());
    }
}
/*ServiceFactoryTest.java
Service Factory Test
Thabiso Kama
18 May 2025
 */
