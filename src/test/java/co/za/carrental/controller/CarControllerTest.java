package co.za.carrental.controller;

import co.za.carrental.domain.Car;
import co.za.carrental.domain.CarType;
import co.za.carrental.domain.Status;
import co.za.carrental.factory.CarFactory;
import co.za.carrental.repository.CarRepository;
import co.za.carrental.repository.CarTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarTypeRepository carTypeRepository;

    private Car testCar;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        carTypeRepository.deleteAll();

        CarType carType = CarType.builder()
                .typeId("T001")
                .name("SUV")
                .dailyRate(500.0f)
                .lateFeePerHour(50.0f)
                .seatingCapacity(5)
                .build();

        carType = carTypeRepository.save(carType);

        testCar = CarFactory.createCar(
                "12345", "Toyota", "Corolla", 2023, Status.AVAILABLE, carType, BigDecimal.valueOf(100.00)
        );
        carRepository.save(testCar);
    }

    @Test
    void create_shouldCreateCar() throws Exception {
        CarType carType = CarType.builder()
                .typeId("T002")
                .name("Sedan")
                .dailyRate(400.0f)
                .lateFeePerHour(40.0f)
                .seatingCapacity(4)
                .build();

        carType = carTypeRepository.save(carType);

        Car newCar = CarFactory.createCar(
                "67890", "Honda", "Civic", 2022, Status.AVAILABLE, carType, BigDecimal.valueOf(120.00)
        );

        String carJson = objectMapper.writeValueAsString(newCar);
        MockMultipartFile carDataPart = new MockMultipartFile("car", "", "application/json", carJson.getBytes(StandardCharsets.UTF_8));
        MockMultipartFile imagePart = new MockMultipartFile("image", "test-image.jpg", "image/jpeg", "test image content".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/cars")
                        .file(carDataPart)
                        .file(imagePart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.carId").value("67890"))
                .andExpect(jsonPath("$.make").value("Honda"))
                .andExpect(jsonPath("$.model").value("Civic"));
    }

    @Test
    void getAll_shouldReturnAllCars() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].carId").value("12345"));
    }

    @Test
    void read_shouldReturnCarById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cars/{carId}", testCar.getCarId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.carId").value("12345"))
                .andExpect(jsonPath("$.make").value("Toyota"));
    }

    @Test
    void update_shouldUpdateCar() throws Exception {
        testCar.setMake("Updated Toyota");
        testCar.setDailyRate(BigDecimal.valueOf(150.00));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/cars/{carId}", testCar.getCarId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testCar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value("Updated Toyota"))
                .andExpect(jsonPath("$.dailyRate").value(150.00));
    }

    @Test
    void delete_shouldDeleteCar() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/cars/{carId}", testCar.getCarId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
