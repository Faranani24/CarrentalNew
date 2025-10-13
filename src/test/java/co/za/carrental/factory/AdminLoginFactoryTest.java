package co.za.carrental.factory;

import co.za.carrental.domain.AdminLogin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AdminLoginFactoryTest {

    @Test
    void testCreateAdminLogin() {
        AdminLogin admin = AdminLoginFactory.createAdminLogin("admin1", "admin@mail.com", "password123", "ADMIN");

        assertNotNull(admin.getAdminId()); // ensure UUID is set
        assertEquals("admin1", admin.getUsername()); // check username
        assertEquals("admin@mail.com", admin.getEmail()); // check email
        assertNotEquals("password123", admin.getPasswordHash()); // ensure password is hashed
        assertEquals("ADMIN", admin.getRole()); // check role
    }
}