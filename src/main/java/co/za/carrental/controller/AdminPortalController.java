package co.za.carrental.controller;

import co.za.carrental.domain.AdminPortal;
import co.za.carrental.service.IAdminPortalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adminportal")
public class AdminPortalController {

    private final IAdminPortalService portalService;

    public AdminPortalController(IAdminPortalService portalService) {
        this.portalService = portalService;
    }

    @PostMapping
    public ResponseEntity<AdminPortal> create(@RequestBody AdminPortal portal) {
        return ResponseEntity.ok(portalService.create(portal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminPortal> read(@PathVariable String id) {
        return portalService.read(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminPortal> update(@PathVariable String id, @RequestBody AdminPortal portal) {
        return ResponseEntity.ok(portalService.update(portal));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        portalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AdminPortal>> getAll() {
        return ResponseEntity.ok(portalService.getAll());
    }
}