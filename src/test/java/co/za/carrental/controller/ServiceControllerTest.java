package co.za.carrental.controller;

import co.za.carrental.domain.Service;
import co.za.carrental.service.IServiceEntityService;
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

@WebMvcTest(ServiceController.class)
class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceEntityService serviceEntityService;

    @Test
    void testGetAllServices() throws Exception {
        Mockito.when(serviceEntityService.getAll()).thenReturn(
                Arrays.asList(
                        new Service.Builder().setServiceId("S1").setName("GPS").setCostPerDay(50).build(),
                        new Service.Builder().setServiceId("S2").setName("WiFi").setCostPerDay(20).build()
                )
        );

        mockMvc.perform(get("/api/services"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("GPS"))
                .andExpect(jsonPath("$[1].name").value("WiFi"));
    }

    @Test
    void testCreateService() throws Exception {
        Service service = new Service.Builder().setServiceId("S1").setName("GPS").setCostPerDay(50).build();
        Mockito.when(serviceEntityService.create(Mockito.any(Service.class))).thenReturn(service);

        mockMvc.perform(post("/api/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"serviceId\":\"S1\",\"name\":\"GPS\",\"costPerDay\":50}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("GPS"));
    }
}
