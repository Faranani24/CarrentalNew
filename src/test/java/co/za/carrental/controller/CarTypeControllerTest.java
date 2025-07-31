package co.za.carrental.controller;

import co.za.carrental.domain.CarType;
import co.za.carrental.factory.CarTypeFactory;
import co.za.carrental.service.ICarTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarTypeControllerTest {

    @Autowired
    private CarTypeController carTypeController;

    @Autowired
    private ICarTypeService carTypeService;

    private CarType testCarType;

    @BeforeEach
    void setUp() {
        testCarType = CarTypeFactory.createCarType(
                "CT001", "SUV", 600.0f, 70.0f, 7
        );
        testCarType = carTypeService.create(testCarType);
    }

    @Test
    void create_shouldCreateCarType() {
        CarType newCarType = CarTypeFactory.createCarType(
                "CT002", "Hatchback", 400.0f, 50.0f, 5
        );
        ResponseEntity<CarType> response = carTypeController.create(newCarType);
        assertNotNull(response.getBody());
        assertEquals("Hatchback", response.getBody().getName());
    }

    @Test
    void read_shouldReturnCarType() {
        ResponseEntity<CarType> response = carTypeController.read(testCarType.getTypeId());
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("SUV", response.getBody().getName());
    }

    @Test
    void update_shouldUpdateCarType() {
        testCarType.setName("Updated SUV");
        ResponseEntity<CarType> response = carTypeController.update(testCarType.getTypeId(), testCarType);
        assertNotNull(response.getBody());
        assertEquals("Updated SUV", response.getBody().getName());
    }

    @Test
    void delete_shouldRemoveCarType() {
        carTypeController.delete(testCarType.getTypeId());
        Optional<CarType> found = carTypeService.read(testCarType.getTypeId());
        assertFalse(found.isPresent());
    }

    @Test
    void getAll_shouldReturnAllCarTypes() {
        ResponseEntity<List<CarType>> response = carTypeController.getAll();
        assertFalse(response.getBody().isEmpty());
    }
}