package co.za.carrental.service.impl;

import co.za.carrental.domain.Admin;
import co.za.carrental.repository.AdminRepository;
import co.za.carrental.service.impl.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAdmin() {
        Admin admin = new Admin.Builder().setAdminId("A1").setEmail("admin@test.com").setPassword("123").build();
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin created = adminService.create(admin);

        assertNotNull(created);
        assertEquals("admin@test.com", created.getEmail());
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    void testGetAllAdmins() {
        when(adminRepository.findAll()).thenReturn(
                Arrays.asList(
                        new Admin.Builder().setAdminId("A1").setEmail("admin1@test.com").setPassword("123").build(),
                        new Admin.Builder().setAdminId("A2").setEmail("admin2@test.com").setPassword("456").build()
                )
        );

        assertEquals(2, adminService.getAll().size());
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    void testGetAdminById() {
        Admin admin = new Admin.Builder().setAdminId("A1").setEmail("admin@test.com").setPassword("123").build();
        when(adminRepository.findById("A1")).thenReturn(Optional.of(admin));

        Admin found = adminService.getById("A1");

        assertNotNull(found);
        assertEquals("admin@test.com", found.getEmail());
        verify(adminRepository, times(1)).findById("A1");
    }

    @Test
    void testDeleteAdmin() {
        doNothing().when(adminRepository).deleteById("A1");

        adminService.delete("A1");

        verify(adminRepository, times(1)).deleteById("A1");
    }
}
