//Faranani Khangale
// Created on 2025/05/25
// 230136982
// This code is part of a car rental system,
// specifically the factory for creating car objects.
// It provides a method to create a car with specified attributes and a car type.


package co.za.carrental.service;

import co.za.carrental.domain.CarType;
import co.za.carrental.repository.CarTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarTypeServiceImplTest {


    private CarTypeRepository repository;
    private CarTypeService service;
    private CarType carType;

    @BeforeEach
    void setUp() {
        repository = mock(CarTypeRepository.class);
        service = new CarTypeServiceImpl(repository);

        carType = new CarType.Builder()
                .setTypeId("SEDAN")
                .setSeatingCapacity(5)
                .setDailyRate(150.0f)
                .setLateFeePerHour(15.0f)
                .build();
    }

    @Test
    void testCreate() {
        when(repository.save(carType)).thenReturn(carType);
        CarType created = service.create(carType);
        assertEquals("SEDAN", created.getTypeId());
    }

    @Test
    void testRead() {
        when(repository.findById("SEDAN")).thenReturn(Optional.of(carType));
        Optional<CarType> result = service.read("SEDAN");
        assertTrue(result.isPresent());
        assertEquals(carType.getDailyRate(), result.get().getDailyRate());
    }

    @Test
    void testUpdate() {
        when(repository.existsById("SEDAN")).thenReturn(true);
        when(repository.save(carType)).thenReturn(carType);
        CarType updated = service.update(carType);
        assertNotNull(updated);
        assertEquals("SEDAN", updated.getTypeId());
    }

    @Test
    void testDelete() {
        service.delete("SEDAN");
        verify(repository, times(1)).deleteById("SEDAN");
    }

    @Test
    void testFindAll() {
        when(repository.findAll()).thenReturn(List.of(carType));
        List<CarType> carTypes = service.findAll();
        assertFalse(carTypes.isEmpty());
        assertEquals(1, carTypes.size());
    }
}
