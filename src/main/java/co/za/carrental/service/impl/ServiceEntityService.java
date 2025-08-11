package co.za.carrental.service.impl;

import co.za.carrental.domain.Service;
import co.za.carrental.repository.ServiceRepository;
import co.za.carrental.service.IServiceEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceEntityService implements IServiceEntityService {

    @Autowired
    private ServiceRepository serviceRepo;

    @Override
    public Service create(Service service) {
        return serviceRepo.save(service);
    }

    @Override
    public List<Service> getAll() {
        return serviceRepo.findAll();
    }

    @Override
    public Service getById(String id) {
        return serviceRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(String id) {
        serviceRepo.deleteById(id);
    }
}
