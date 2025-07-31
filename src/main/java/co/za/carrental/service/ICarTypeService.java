package co.za.carrental.service;

import co.za.carrental.domain.CarType;

import java.util.List;
import java.util.Optional;

public interface ICarTypeService {
    CarType create(CarType carType);
    CarType update(CarType carType);
    void delete(String id);
    List<CarType> getAll();
    Optional<CarType> read(String id);
}