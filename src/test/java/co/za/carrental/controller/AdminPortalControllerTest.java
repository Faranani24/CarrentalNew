package co.za.carrental.controller;

import co.za.carrental.domain.AdminPortal;
import co.za.carrental.factory.AdminPortalFactory;
import co.za.carrental.service.IAdminPortalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // starts Spring for testing controllers
class AdminPortalControllerTest {

    @Autowired
    private AdminPortalController portalController;

    @Autowired
    private IAdminPortalService portalService;

    private AdminPortal testPortal;

    @BeforeEach
    void setUp() {
        portalService.getAll().forEach(p -> portalService.delete(p.getPortalId())); // clear all
        testPortal = portalService.create(
                AdminPortalFactory.createPortal("Control Center", "Admin control area")
        );
    }

    @Test
    void create_shouldCreatePortal() {
        AdminPortal newPortal = AdminPortalFactory.createPortal("Monitoring", "System monitoring");
        ResponseEntity<AdminPortal> response = portalController.create(newPortal);

        assertNotNull(response.getBody());
        assertEquals("Monitoring", response.getBody().getPortalName());
    }

    @Test
    void read_shouldReturnPortal() {
        ResponseEntity<AdminPortal> response = portalController.read(testPortal.getPortalId());
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("Control Center", response.getBody().getPortalName());
    }

    @Test
    void update_shouldUpdatePortal() {
        testPortal = new AdminPortal(testPortal.getPortalId(), "Updated Center", "Changed");
        ResponseEntity<AdminPortal> response = portalController.update(testPortal.getPortalId(), testPortal);

        assertEquals("Updated Center", response.getBody().getPortalName());
    }

    @Test
    void delete_shouldRemovePortal() {
        portalController.delete(testPortal.getPortalId());
        Optional<AdminPortal> found = portalService.read(testPortal.getPortalId());
        assertFalse(found.isPresent());
    }

    @Test
    void getAll_shouldReturnAllPortals() {
        ResponseEntity<List<AdminPortal>> response = portalController.getAll();
        assertFalse(response.getBody().isEmpty());
    }
}