// Faranani Khangale
// Created on 2025/05/25
// 230136982
// This code is part of a car rental system,
// specifically the controller for managing car entities.
package co.za.carrental.controller;

import co.za.carrental.domain.Car;
import co.za.carrental.domain.CarType;
import co.za.carrental.domain.Status;
import co.za.carrental.service.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)


public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    private Car car;

    @BeforeEach
    void setUp() {
        CarType carType = new CarType("type1", "Sedan"); // Replace with actual constructor
        car = new Car.Builder()
                .setCarId("car1")
                .setLicensePlate("CA123456")
                .setMake("Toyota")
                .setModel("Corolla")
                .setYear(2020)
                .setStatus(Status.AVAILABLE) // Ensure Status is an enum
                .setCarType(carType)
                .build();
    }

    @Test
    void testCreateCar() throws Exception {
        Mockito.when(carService.create(any(Car.class))).thenReturn(car);

        mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(car)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId").value("car1"))
                .andExpect(jsonPath("$.make").value("Toyota"))
                .andExpect(jsonPath("$.status").value("AVAILABLE"));
    }

    @Test
    void testGetCarById() throws Exception {
        Mockito.when(carService.read("car1")).thenReturn(Optional.of(car));

        mockMvc.perform(get("/api/cars/car1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Corolla"))
                .andExpect(jsonPath("$.licensePlate").value("CA123456"));
    }

    @Test
    void testGetAllCars() throws Exception {
        Mockito.when(carService.findAll()).thenReturn(List.of(car));

        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].year").value(2020))
                .andExpect(jsonPath("$[0].carType.typeId").value("type1"));

    }

    @Test
    void testDeleteCar() throws Exception {
        Mockito.doNothing().when(carService).delete("car1");

        mockMvc.perform(delete("/api/cars/car1"))
                .andExpect(status().isNoContent());
    }
}
