package co.za.carrental.factory;

import co.za.carrental.domain.Service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ServiceFactoryTest {

    @Test
    void testCreateService() {
        Service service = ServiceFactory.createService("S1", "GPS", 50.0f);

        assertNotNull(service);
        assertEquals("S1", service.getServiceId());
        assertEquals("GPS", service.getName());
        assertEquals(50.0f, service.getCostPerDay());
    }
}
