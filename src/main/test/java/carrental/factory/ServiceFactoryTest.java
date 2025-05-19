package co.za.carrental.factory;

import co.za.carrental.domain.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

class ServiceFactoryTest {
    @Test
    void testCreateService(){
        assertNotNull(service);
    }
}