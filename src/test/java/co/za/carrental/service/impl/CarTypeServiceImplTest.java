package co.za.carrental.service.impl;

import co.za.carrental.domain.CarType;
import co.za.carrental.factory.CarTypeFactory;
import co.za.carrental.service.ICarTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CarTypeServiceImplTest {

    @Autowired
    private ICarTypeService carTypeService;

    private CarType testCarType;

    @BeforeEach
    void setUp() {
        testCarType = CarTypeFactory.createCarType(
                "CT001", "SUV", 600.0f, 70.0f, 7
        );
        testCarType = carTypeService.create(testCarType);
        assertNotNull(testCarType.getTypeId());
    }

    @Test
    void create_shouldSaveCarType() {
        CarType newCarType = CarTypeFactory.createCarType(
                "CT002", "Hatchback", 400.0f, 50.0f, 5
        );
        CarType savedCarType = carTypeService.create(newCarType);
        assertNotNull(savedCarType);
        assertEquals("Hatchback", savedCarType.getName());
    }

    @Test
    void update_shouldUpdateCarType() {
        testCarType.setName("Updated SUV");
        CarType updatedCarType = carTypeService.update(testCarType);
        assertNotNull(updatedCarType);
        assertEquals("Updated SUV", updatedCarType.getName());
    }

    @Test
    void delete_shouldRemoveCarTypeById() {
        carTypeService.delete(testCarType.getTypeId());
        Optional<CarType> found = carTypeService.read(testCarType.getTypeId());
        assertFalse(found.isPresent());
    }

    @Test
    void getAll_shouldReturnAllCarTypes() {
        List<CarType> carTypes = carTypeService.getAll();
        assertFalse(carTypes.isEmpty());
        assertEquals("SUV", carTypes.get(0).getName());
    }
}