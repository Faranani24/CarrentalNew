
package co.za.carrental.service.impl;

import co.za.carrental.domain.AdminPortal;
import co.za.carrental.factory.AdminPortalFactory;
import co.za.carrental.repository.AdminPortalRepository;
import co.za.carrental.service.IAdminPortalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // spins up Spring context
class AdminPortalServiceImplTest {

    @Autowired
    private IAdminPortalService portalService;

    @Autowired
    private AdminPortalRepository repository;

    private AdminPortal testPortal;

    @BeforeEach
    void setUp() {
        repository.deleteAll(); // clean DB before each test
        testPortal = portalService.create(
                AdminPortalFactory.createPortal("Dashboard", "Handles overall admin tasks")
        );
    }

    @Test
    void create_shouldSavePortal() {
        AdminPortal portal = AdminPortalFactory.createPortal("User Management", "Handles users");
        AdminPortal saved = portalService.create(portal);

        assertNotNull(saved.getPortalId()); // ID must be generated
        assertEquals("User Management", saved.getPortalName());
    }

    @Test
    void read_shouldReturnPortal() {
        Optional<AdminPortal> found = portalService.read(testPortal.getPortalId());
        assertTrue(found.isPresent());
        assertEquals("Dashboard", found.get().getPortalName());
    }

    @Test
    void update_shouldModifyPortal() {
        testPortal = new AdminPortal(testPortal.getPortalId(), "Updated Dashboard", "Updated description");
        AdminPortal updated = portalService.update(testPortal);

        assertEquals("Updated Dashboard", updated.getPortalName());
    }

    @Test
    void delete_shouldRemovePortal() {
        portalService.delete(testPortal.getPortalId());
        assertFalse(portalService.read(testPortal.getPortalId()).isPresent());
    }

    @Test
    void getAll_shouldReturnList() {
        List<AdminPortal> portals = portalService.getAll();
        assertFalse(portals.isEmpty()); // list shouldn’t be empty
    }
}
