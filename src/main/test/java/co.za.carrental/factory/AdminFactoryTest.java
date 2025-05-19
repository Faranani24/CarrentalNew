package java.co.za.carrental.factory;

import co.za.carrental.domain.Admin;
import co.za.carrental.factory.AdminFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class  AdminFactoryTest {
    @Test
    void testCreateAdmin(){
        Admin admin = AdminFactory.createAdmin("A001", "admin@gmail.com","securePass");
        assertNotNull(admin);
        assertNotNull("A001", admin.getAdminId());
        assertNotNull("admin@gmail.com", admin.getEmail());
        assertNotNull("securePass", admin.getPassword());
    }
}
/*AdminFactoryTest.java
Admin Factory Test class
Thabiso Kama
18 May 2025
 */