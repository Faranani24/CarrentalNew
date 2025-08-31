package co.za.carrental.controller;

import co.za.carrental.domain.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateAndFetchAdmin() {
        String baseUrl = "http://localhost:" + port + "/api/admins";

        Admin admin = new Admin.Builder()
                .setAdminId("A003")
                .setEmail("controlleradmin@example.com")
                .setPassword("securepass")
                .build();

        restTemplate.postForEntity(baseUrl, admin, Admin.class);

        Admin fetched = restTemplate.getForObject(baseUrl + "/A003", Admin.class);

        assertNotNull(fetched);
        assertEquals("controlleradmin@example.com", fetched.getEmail());
    }
  }
