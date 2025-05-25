package co.za.carrental.service;

import co.za.carrental.domain.CarType;

import java.util.List;
import java.util.Optional;


public interface CarTypeService {
    CarType create(CarType carType);
    Optional<CarType> read(String typeId);
    CarType update(CarType carType);
    void delete(String typeId);
    List<CarType> findAll();
}
