package co.za.carrental.service;

import co.za.carrental.domain.AdminLogin;
import co.za.carrental.factory.AdminLoginFactory;
import co.za.carrental.repository.AdminLoginRepository;
import co.za.carrental.service.impl.AdminLoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminLoginServiceTest {

    private AdminLoginRepository repository;
    private IAdminLoginService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(AdminLoginRepository.class); // mock repo
        service = new AdminLoginServiceImpl(repository); // inject mock
    }

    @Test
    void testAuthenticateSuccess() {
        AdminLogin admin = AdminLoginFactory.createAdminLogin("user1", "u1@mail.com", "mypassword", "ADMIN");

        when(repository.findByUsername("user1")).thenReturn(Optional.of(admin));

        Optional<AdminLogin> result = service.authenticate("user1", "mypassword");
        assertTrue(result.isPresent());
    }

    @Test
    void testAuthenticateFailure() {
        when(repository.findByUsername("badUser")).thenReturn(Optional.empty());

        Optional<AdminLogin> result = service.authenticate("badUser", "password");
        assertFalse(result.isPresent());
    }
}