package co.za.carrental.service;

import co.za.carrental.domain.Admin;
import co.za.carrental.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepo;

    public Admin create(Admin admin) {
        return adminRepo.save(admin);
    }

    public List<Admin> getAll() {
        return adminRepo.findAll();
    }

    public Admin getById(String id) {
        return adminRepo.findById(id).orElse(null);
    }

    public void delete(String id) {
        adminRepo.deleteById(id);
    }
}
/*AdminService.java
Admin CarService class
Thabiso Kama
25 May 2025
 */
