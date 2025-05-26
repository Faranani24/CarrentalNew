package co.za.carrental.service;



import co.za.carrental.domain.CarService;

import java.util.List;

public interface ICarService extends IService<CarService, Integer>{

    List<CarService> getAll();
}

