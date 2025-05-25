package co.za.carrental.service;

import co.za.carrental.domain.CarType;
import co.za.carrental.repository.CarTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarTypeServiceImpl implements CarTypeService {

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
    public Optional<CarType> read(String typeId) {
        return carTypeRepository.findById(typeId);
    }

    @Override
    public CarType update(CarType carType) {
        if (carTypeRepository.existsById(carType.getTypeId())) {
            return carTypeRepository.save(carType);
        }
        return null;
    }

    @Override
    public void delete(String typeId) {
        carTypeRepository.deleteById(typeId);
    }

    @Override
    public List<CarType> findAll() {
        return carTypeRepository.findAll();
    }
}
