package co.za.carrental.controller;

import co.za.carrental.domain.Admin;
import co.za.carrental.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admins")

public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.create(admin);
    }

    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAll();
    }

    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable String id) {
        return adminService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable String id) {
        adminService.delete(id);
    }
}
/*AdminController.java
Admin Controller class
Thabiso Kama
25 May 2025
*/