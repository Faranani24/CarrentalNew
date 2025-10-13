
package co.za.carrental.service.impl;

import co.za.carrental.domain.AdminPortal;
import co.za.carrental.repository.AdminPortalRepository;
import co.za.carrental.service.IAdminPortalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marks this as a Spring service
public class AdminPortalServiceImpl implements IAdminPortalService {

    private final AdminPortalRepository repository; // Dependency on repository

    // Constructor injection (Spring will auto-wire this)
    public AdminPortalServiceImpl(AdminPortalRepository repository) {
        this.repository = repository;
    }

    @Override
    public AdminPortal create(AdminPortal portal) {
        return repository.save(portal); // Save to DB
    }

    @Override
    public Optional<AdminPortal> read(String id) {
        return repository.findById(id); // Lookup portal by ID
    }

    @Override
    public AdminPortal update(AdminPortal portal) {
        return repository.save(portal); // Save replaces old record
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id); // Delete record by ID
    }

    @Override
    public List<AdminPortal> getAll() {
        return repository.findAll(); // Get all records
    }
}
