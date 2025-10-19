
package co.za.carrental.service.impl;

import co.za.carrental.domain.AdminPortal;
import co.za.carrental.repository.AdminPortalRepository;
import co.za.carrental.service.IAdminPortalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminPortalServiceImpl implements IAdminPortalService {

    private final AdminPortalRepository repository;


    public AdminPortalServiceImpl(AdminPortalRepository repository) {
        this.repository = repository;
    }

    @Override
    public AdminPortal create(AdminPortal portal) {
        return repository.save(portal);
    }

    @Override
    public Optional<AdminPortal> read(String id) {
        return repository.findById(id);
    }

    @Override
    public AdminPortal update(AdminPortal portal) {
        return repository.save(portal);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<AdminPortal> getAll() {
        return repository.findAll();
    }
}
