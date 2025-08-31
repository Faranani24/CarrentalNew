package co.za.carrental.controller;

import co.za.carrental.domain.Service;
import co.za.carrental.service.IServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private IServiceEntityService serviceEntityService;

    @PostMapping
    public Service createService(@RequestBody Service service) {
        return serviceEntityService.create(service);
    }

    @GetMapping
    public List<Service> getAllServices() {
        return serviceEntityService.getAll();
    }

    @GetMapping("/{id}")
    public Service getServiceById(@PathVariable String id) {
        return serviceEntityService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable String id) {
        serviceEntityService.delete(id);
    }
}
