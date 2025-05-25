//Faranani Khangale
// Created on 2025/05/25
// 230136982
// This code is part of a car rental system,
//specifically the controller for managing car types.



package co.za.carrental.controller;

import co.za.carrental.domain.CarType;
import co.za.carrental.service.CarTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarTypeControllerTest {

    @Mock
    private CarTypeService service;

    @InjectMocks
    private CarTypeController controller;

    private CarType sampleCarType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleCarType = new CarType.Builder()
                .setTypeId("SEDAN")
                .setSeatingCapacity(5)
                .setDailyRate(150.0f)
                .setLateFeePerHour(15.0f)
                .build();
    }

    @Test
    void testCreate() {
        when(service.create(any(CarType.class))).thenReturn(sampleCarType);
        ResponseEntity<CarType> response = controller.create(sampleCarType);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleCarType, response.getBody());
        verify(service).create(sampleCarType);
    }

    @Test
    void testRead() {
        when(service.read("SEDAN")).thenReturn(Optional.of(sampleCarType));
        ResponseEntity<CarType> response = controller.read("SEDAN");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleCarType, response.getBody());
        verify(service).read("SEDAN");
    }

    @Test
    void testUpdate() {
        when(service.update(any(CarType.class))).thenReturn(sampleCarType);
        ResponseEntity<CarType> response = controller.update(sampleCarType);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleCarType, response.getBody());
        verify(service).update(sampleCarType);
    }

    @Test
    void testDelete() {
        doNothing().when(service).delete("SEDAN");
        ResponseEntity<Void> response = controller.delete("SEDAN");

        assertEquals(204, response.getStatusCodeValue());
        verify(service).delete("SEDAN");
    }
}
