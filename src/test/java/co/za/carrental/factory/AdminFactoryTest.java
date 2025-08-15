package co.za.carrental.factory;

import co.za.carrental.domain.Admin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminFactoryTest {

    @Test
    void testCreateAdmin() {
        Admin admin = AdminFactory.createAdmin("A1", "pass123", "admin@test.com");

        assertNotNull(admin);
        assertEquals("A1", admin.getAdminId());
        assertEquals("admin@test.com", admin.getEmail());
        assertEquals("pass123", admin.getPassword());
    }
}
