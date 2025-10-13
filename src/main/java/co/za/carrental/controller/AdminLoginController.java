package co.za.carrental.controller; // package for REST controllers

import co.za.carrental.domain.AdminLogin; // import entity
import co.za.carrental.service.IAdminLoginService;
import org.springframework.beans.factory.annotation.Autowired; // DI
import org.springframework.http.ResponseEntity; // HTTP response wrapper
import org.springframework.web.bind.annotation.*; // REST mapping annotations


import java.util.List; // return multiple admins
import java.util.Optional; // for nullable values


@RestController // marks this class as REST controller
@RequestMapping("/admin-login") // base URL path

public class AdminLoginController {


    private final IAdminLoginService service;

    @Autowired // inject service
    public AdminLoginController(IAdminLoginService service) {
        this.service = service;
    }

    // CREATE new admin
    @PostMapping("/create")
    public ResponseEntity<AdminLogin> create(@RequestBody AdminLogin adminLogin) {
        AdminLogin newAdmin = service.create(adminLogin);
        return ResponseEntity.ok(newAdmin); // 200 OK with created object
    }

    // READ by ID
    @GetMapping("/read/{id}")
    public ResponseEntity<AdminLogin> read(@PathVariable String id) {
        return service.read(id)
                .map(ResponseEntity::ok) // return 200 if found
                .orElse(ResponseEntity.notFound().build()); // 404 if not found
    }

    // UPDATE existing admin
    @PutMapping("/update")
    public ResponseEntity<AdminLogin> update(@RequestBody AdminLogin adminLogin) {
        AdminLogin updated = service.update(adminLogin);
        return ResponseEntity.ok(updated);
    }

    // DELETE by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // GET ALL
    @GetMapping("/all")
    public ResponseEntity<List<AdminLogin>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // LOGIN (authenticate)
    @PostMapping("/login")
    public ResponseEntity<AdminLogin> login(@RequestParam String username, @RequestParam String password) {
        Optional<AdminLogin> admin = service.authenticate(username, password);
        return admin.map(ResponseEntity::ok) // 200 OK if valid login
                .orElse(ResponseEntity.status(401).build()); // 401 Unauthorized if invalid
    }
}