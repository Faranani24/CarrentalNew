package co.za.carrental.factory;

import co.za.carrental.domain.Admin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminFactoryTest {

    @Test
    void testCreateAdmin() {
        Admin admin = AdminFactory.createAdmin("A001", "admin@example.com", "password123");

        assertNotNull(admin);
        assertEquals("A001", admin.getAdminId());
        assertEquals("admin@example.com", admin.getEmail());
        assertEquals("password123", admin.getPassword());
    }
}
