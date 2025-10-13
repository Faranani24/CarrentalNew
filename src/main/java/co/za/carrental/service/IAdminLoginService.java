package co.za.carrental.service; // package for services — business logic layer

import co.za.carrental.domain.AdminLogin; // import entity
import java.util.List; // for returning multiple AdminLogins
import java.util.Optional; // for safe nullable handling

public interface IAdminLoginService {
    // Interface defines what our service must do (implementation later)

    // Create new admin
    AdminLogin create(AdminLogin adminLogin);

    // Read admin by ID
    Optional<AdminLogin> read(String adminId);

    // Update admin details
    AdminLogin update(AdminLogin adminLogin);

    // Delete admin by ID
    void delete(String adminId);

    // Get all admins
    List<AdminLogin> getAll();

    // Authenticate admin (username + password)
    Optional<AdminLogin> authenticate(String username, String rawPassword);
}