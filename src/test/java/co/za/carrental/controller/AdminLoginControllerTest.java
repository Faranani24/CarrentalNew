package co.za.carrental.controller;

import co.za.carrental.domain.AdminLogin;
import co.za.carrental.factory.AdminLoginFactory;
import co.za.carrental.service.IAdminLoginService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminLoginControllerTest {

    private final IAdminLoginService service = Mockito.mock(IAdminLoginService.class);
    private final AdminLoginController controller = new AdminLoginController(service);

    @Test
    void testLoginSuccess() {
        AdminLogin admin = AdminLoginFactory.createAdminLogin("controllerUser", "c@mail.com", "pass123", "ADMIN");
        when(service.authenticate("controllerUser", "pass123")).thenReturn(Optional.of(admin));

        ResponseEntity<AdminLogin> response = controller.login("controllerUser", "pass123");
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.hasBody());
    }

    @Test
    void testLoginFailure() {
        when(service.authenticate("badUser", "wrong")).thenReturn(Optional.empty());

        ResponseEntity<AdminLogin> response = controller.login("badUser", "wrong");
        assertEquals(401, response.getStatusCodeValue());
    }
}