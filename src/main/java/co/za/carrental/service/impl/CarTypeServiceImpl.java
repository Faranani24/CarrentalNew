package co.za.carrental.service.impl;

import co.za.carrental.domain.CarType;
import co.za.carrental.repository.CarTypeRepository;
import co.za.carrental.service.ICarTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarTypeServiceImpl implements ICarTypeService {

    private final CarTypeRepository carTypeRepository;

    @Autowired
    public CarTypeServiceImpl(CarTypeRepository carTypeRepository) {
        this.carTypeRepository = carTypeRepository;
    }

    @Override
    public CarType create(CarType carType) {
        return carTypeRepository.save(carType);
    }

    @Override
    public CarType update(CarType carType) {
        return carTypeRepository.save(carType);
    }

    @Override
    public void delete(String id) {
        carTypeRepository.deleteById(id);
    }

    @Override
    public List<CarType> getAll() {
        return carTypeRepository.findAll();
    }

    @Override
    public Optional<CarType> read(String id) {
        return carTypeRepository.findById(id);
    }

}