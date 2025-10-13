package co.za.carrental.factory;

import co.za.carrental.domain.AdminPortal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminPortalFactoryTest {

    @Test
    void createPortal_success() {
        // create portal with valid data
        AdminPortal portal = AdminPortalFactory.createPortal("Main Admin Portal", "Manages system resources");

        assertNotNull(portal); // not null
        assertEquals("Main Admin Portal", portal.getPortalName()); // name matches
        assertEquals("Manages system resources", portal.getDescription()); // desc matches
    }

    @Test
    void createPortal_fail_whenNameIsBlank() {
        // expect exception if portal name is blank
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> AdminPortalFactory.createPortal("", "Invalid Portal"));

        assertEquals("Portal name required", exception.getMessage());
    }
}