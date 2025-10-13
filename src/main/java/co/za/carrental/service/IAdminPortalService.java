package co.za.carrental.service;

import co.za.carrental.domain.AdminPortal;
import java.util.List;
import java.util.Optional;

// Defines the contract for AdminPortal services
public interface IAdminPortalService {
    AdminPortal create(AdminPortal portal); // Create new portal
    Optional<AdminPortal> read(String id);  // Read portal by ID
    AdminPortal update(AdminPortal portal); // Update existing portal
    void delete(String id);                 // Delete by ID
    List<AdminPortal> getAll();             // Return all portals
}