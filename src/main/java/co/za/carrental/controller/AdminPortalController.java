package co.za.carrental.controller;

import co.za.carrental.domain.AdminPortal;
import co.za.carrental.service.IAdminPortalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Tells Spring this handles REST endpoints
@RequestMapping("/api/adminportal") // Base URL for all endpoints here
public class AdminPortalController {

    private final IAdminPortalService portalService;

    public AdminPortalController(IAdminPortalService portalService) {
        this.portalService = portalService;
    }

    @PostMapping // POST /api/adminportal
    public ResponseEntity<AdminPortal> create(@RequestBody AdminPortal portal) {
        return ResponseEntity.ok(portalService.create(portal));
    }

    @GetMapping("/{id}") // GET /api/adminportal/{id}
    public ResponseEntity<AdminPortal> read(@PathVariable String id) {
        return portalService.read(id)
                .map(ResponseEntity::ok) // if present, return 200
                .orElse(ResponseEntity.notFound().build()); // if not found, 404
    }

    @PutMapping("/{id}") // PUT /api/adminportal/{id}
    public ResponseEntity<AdminPortal> update(@PathVariable String id, @RequestBody AdminPortal portal) {
        return ResponseEntity.ok(portalService.update(portal));
    }

    @DeleteMapping("/{id}") // DELETE /api/adminportal/{id}
    public ResponseEntity<Void> delete(@PathVariable String id) {
        portalService.delete(id);
        return ResponseEntity.noContent().build(); // Return 204
    }

    @GetMapping // GET /api/adminportal
    public ResponseEntity<List<AdminPortal>> getAll() {
        return ResponseEntity.ok(portalService.getAll());
    }
}