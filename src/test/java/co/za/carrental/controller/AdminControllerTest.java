package co.za.carrental.controller;

import co.za.carrental.domain.Admin;
import co.za.carrental.service.IAdminService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAdminService adminService;

    @Test
    void testGetAllAdmins() throws Exception {
        Mockito.when(adminService.getAll()).thenReturn(
                Arrays.asList(
                        new Admin.Builder().setAdminId("A1").setEmail("admin1@test.com").setPassword("123").build(),
                        new Admin.Builder().setAdminId("A2").setEmail("admin2@test.com").setPassword("456").build()
                )
        );

        mockMvc.perform(get("/api/admins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("admin1@test.com"))
                .andExpect(jsonPath("$[1].email").value("admin2@test.com"));
    }

    @Test
    void testCreateAdmin() throws Exception {
        Admin admin = new Admin.Builder().setAdminId("A1").setEmail("admin@test.com").setPassword("pass").build();
        Mockito.when(adminService.create(Mockito.any(Admin.class))).thenReturn(admin);

        mockMvc.perform(post("/api/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"adminId\":\"A1\",\"email\":\"admin@test.com\",\"password\":\"pass\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@test.com"));
    }
}
