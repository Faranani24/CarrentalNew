package co.za.carrental.service.impl;

import co.za.carrental.domain.Admin;
import co.za.carrental.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private AdminService adminService;

    @Test
    void testCreateAndGetAdmin() {
        Admin admin = new Admin.Builder()
                .setAdminId("A002")
                .setEmail("testadmin@example.com")
                .setPassword("secret")
                .build();

        adminService.create(admin);

        Admin fetched = adminService.getById("A002");

        assertNotNull(fetched);
        assertEquals("testadmin@example.com", fetched.getEmail());
    }
}
