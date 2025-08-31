package co.za.carrental.controller;

import co.za.carrental.domain.Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServiceControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateAndFetchService() {
        String baseUrl = "http://localhost:" + port + "/api/services";

        Service service = new Service.Builder()
                .setServiceId("S003")
                .setName("WiFi")
                .setCostPerDay(25.0f)
                .build();

        restTemplate.postForEntity(baseUrl, service, Service.class);

        Service fetched = restTemplate.getForObject(baseUrl + "/S003", Service.class);

        assertNotNull(fetched);
        assertEquals("WiFi", fetched.getName());
        assertEquals(25.0f, fetched.getCostPerDay());
    }
                                   }
