package co.za.carrental.service.impl;

import co.za.carrental.domain.Service;
import co.za.carrental.repository.ServiceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceEntityServiceTest {

    @Autowired
    private ServiceRepository serviceRepo;

    @Autowired
    private ServiceEntityService serviceEntityService;

    @Test
    void testCreateAndGetService() {
        Service service = new Service.Builder()
                .setServiceId("S002")
                .setName("Child Seat")
                .setCostPerDay(75.0f)
                .build();

        serviceEntityService.create(service);

        Service fetched = serviceEntityService.getById("S002");

        assertNotNull(fetched);
        assertEquals("Child Seat", fetched.getName());
        assertEquals(75.0f, fetched.getCostPerDay());
    }
  }
