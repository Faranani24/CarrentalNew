package co.za.carrental.service;

import co.za.carrental.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.za.carrental.domain.CarService;

import java.util.List;

@Service
public class CarServiceImpl implements ICarService {

    private final ServiceRepository serviceRepo;

    @Autowired
    public CarServiceImpl(ServiceRepository serviceRepo) {
        this.serviceRepo = serviceRepo; }

    @Override
    public CarService create(CarService carService) {
        return serviceRepo.save(carService);
    }

    @Override
    public List<CarService> getAll() {
        return serviceRepo.findAll();
    }

    @Override
    public CarService read(Integer id) {
        return serviceRepo.findById(id).orElse(null);
    }


    @Override
    public CarService update(CarService carService) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        serviceRepo.deleteById(id);
    }

}
