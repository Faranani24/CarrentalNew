package co.za.carrental.service.impl;

// Removed unused imports like Booking and Customer to keep it clean,
// unless they are explicitly used in other methods of this specific class.
import co.za.carrental.domain.CarService;
import co.za.carrental.repository.ServiceRepository; // The repository for CarService
import co.za.carrental.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements ICarService {

    private final ServiceRepository serviceRepo; // Renamed from 'repository' for clarity

    @Autowired
    public CarServiceImpl(ServiceRepository serviceRepo) {
        this.serviceRepo = serviceRepo;
    }

    @Override
    public CarService create(CarService carService) {
        return serviceRepo.save(carService);
    }

    @Override
    public List<CarService> getAll() {
        return serviceRepo.findAll();
    }

    // Corrected: Return type Optional<CarService>, ID type String
    @Override
    public Optional<CarService> read(String id) { // ID type changed to String
        return serviceRepo.findById(id); // Returns Optional<CarService> directly
    }

    @Override
    public CarService update(CarService carService) {

        if (carService.getServiceId() == null || !serviceRepo.existsById(carService.getServiceId())) {

            throw new IllegalArgumentException("Cannot update car service: ID is null or service does not exist.");
        }
        return serviceRepo.save(carService); // Saves changes to existing entity
    }

    @Override
    public void delete(String id) { // ID type changed to String

        if (serviceRepo.existsById(id)) {
            serviceRepo.deleteById(id);
        } else {

            System.out.println("Attempted to delete non-existent CarService with ID: " + id);
        }
    }

}