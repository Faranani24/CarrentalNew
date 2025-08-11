package co.za.carrental.service.impl;

import co.za.carrental.domain.Admin;
import co.za.carrental.repository.AdminRepository;
import co.za.carrental.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService implements IAdminService {

    @Autowired
    private AdminRepository adminRepo;

    @Override
    public Admin create(Admin admin) {
        return adminRepo.save(admin);
    }

    @Override
    public List<Admin> getAll() {
        return adminRepo.findAll();
    }

    @Override
    public Admin getById(String id) {
        return adminRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(String id) {
        adminRepo.deleteById(id);
    }
}
