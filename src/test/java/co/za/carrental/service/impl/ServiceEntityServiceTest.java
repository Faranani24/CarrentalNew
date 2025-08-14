package co.za.carrental.service.impl;

import co.za.carrental.domain.Service;
import co.za.carrental.repository.ServiceRepository;
import co.za.carrental.service.impl.ServiceEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceEntityServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private ServiceEntityService serviceEntityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateService() {
        Service service = new Service.Builder().setServiceId("S1").setName("GPS").setCostPerDay(50).build();
        when(serviceRepository.save(service)).thenReturn(service);

        Service created = serviceEntityService.create(service);

        assertNotNull(created);
        assertEquals("GPS", created.getName());
        verify(serviceRepository, times(1)).save(service);
    }

    @Test
    void testGetAllServices() {
        when(serviceRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Service.Builder().setServiceId("S1").setName("GPS").setCostPerDay(50).build(),
                        new Service.Builder().setServiceId("S2").setName("WiFi").setCostPerDay(20).build()
                )
        );

        assertEquals(2, serviceEntityService.getAll().size());
        verify(serviceRepository, times(1)).findAll();
    }

    @Test
    void testGetServiceById() {
        Service service = new Service.Builder().setServiceId("S1").setName("GPS").setCostPerDay(50).build();
        when(serviceRepository.findById("S1")).thenReturn(Optional.of(service));

        Service found = serviceEntityService.getById("S1");

        assertNotNull(found);
        assertEquals("GPS", found.getName());
        verify(serviceRepository, times(1)).findById("S1");
    }

    @Test
    void testDeleteService() {
        doNothing().when(serviceRepository).deleteById("S1");

        serviceEntityService.delete("S1");

        verify(serviceRepository, times(1)).deleteById("S1");
    }
}
