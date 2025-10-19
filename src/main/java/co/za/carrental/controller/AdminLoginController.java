package co.za.carrental.controller;

import co.za.carrental.domain.AdminLogin; // import entity
import co.za.carrental.service.IAdminLoginService;
import org.springframework.beans.factory.annotation.Autowired; // DI
import org.springframework.http.ResponseEntity; // HTTP response wrapper
import org.springframework.web.bind.annotation.*; // REST mapping annotations


import java.util.List; // return multiple admins
import java.util.Optional; // for nullable values


@RestController
@RequestMapping("/admin-login")

public class AdminLoginController {


    private final IAdminLoginService service;

    @Autowired
    public AdminLoginController(IAdminLoginService service) {
        this.service = service;
    }


    @PostMapping("/create")
    public ResponseEntity<AdminLogin> create(@RequestBody AdminLogin adminLogin) {
        AdminLogin newAdmin = service.create(adminLogin);
        return ResponseEntity.ok(newAdmin);
    }


    @GetMapping("/read/{id}")
    public ResponseEntity<AdminLogin> read(@PathVariable String id) {
        return service.read(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/update")
    public ResponseEntity<AdminLogin> update(@RequestBody AdminLogin adminLogin) {
        AdminLogin updated = service.update(adminLogin);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/all")
    public ResponseEntity<List<AdminLogin>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }


    @PostMapping("/login")
    public ResponseEntity<AdminLogin> login(@RequestParam String username, @RequestParam String password) {
        Optional<AdminLogin> admin = service.authenticate(username, password);
        return admin.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}