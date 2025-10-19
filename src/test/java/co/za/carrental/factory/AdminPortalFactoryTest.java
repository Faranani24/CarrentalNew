package co.za.carrental.factory;

import co.za.carrental.domain.AdminPortal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminPortalFactoryTest {

    @Test
    void createPortal_success() {

        AdminPortal portal = AdminPortalFactory.createPortal("Main Admin Portal", "Manages system resources");

        assertNotNull(portal);
        assertEquals("Main Admin Portal", portal.getPortalName());
        assertEquals("Manages system resources", portal.getDescription());
    }

    @Test
    void createPortal_fail_whenNameIsBlank() {

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> AdminPortalFactory.createPortal("", "Invalid Portal"));

        assertEquals("Portal name required", exception.getMessage());
    }
}