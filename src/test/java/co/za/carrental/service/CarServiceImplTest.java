
// Faranani Khangale
// Created on 2025/05/25
// 230136982
// This code is part of a car rental system,
// specifically the service implementation for managing car entities.
// It provides methods for creating, reading, updating, and deleting car records,
package co.za.carrental.service;

import co.za.carrental.domain.Car;
import co.za.carrental.domain.CarType;
import co.za.carrental.domain.Status;
import co.za.carrental.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarServiceImplTest {

    private CarRepository repository;
    private CarService service;
    private Car car;


    @BeforeEach
    void setUp() {
        repository = mock(CarRepository.class);
        service = new CarServiceImpl(repository);

        car = new Car.Builder()
                .setCarId("CAR101")
                .setLicensePlate("CA123456")
                .setMake("Toyota")
                .setModel("Corolla")
                .setYear(2022)
                .setStatus(Status.AVAILABLE)
                .setCarType(CarType.SEDAN)
                .build();
    }

    @Test
    void testCreate() {
        when(repository.save(car)).thenReturn(car);
        Car created = service.create(car);
        assertEquals("CAR101", created.getCarId());

    }

    @Test
    void testRead() {
        when(repository.findById("CAR101")).thenReturn(Optional.of(car));
        Optional<Car> result = service.read("CAR101");
        assertTrue(result.isPresent());
        assertEquals("CAR101", result.get().getCarId());
    }

    @Test
    void testDelete() {
        service.delete("CAR101");
        verify(repository, times(1)).deleteById("CAR101");
    }

    @Test
    void testGetAll() {
        when(repository.findAll()).thenReturn(List.of(car));
        List<Car> result = service.findAll();
        assertEquals(1, result.size());
    }
}
