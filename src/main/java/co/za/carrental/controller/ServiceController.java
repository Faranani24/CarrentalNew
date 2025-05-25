package co.za.carrental.controller;

import co.za.carrental.domain.CarService;
import co.za.carrental.service.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")

public class ServiceController {
    @Autowired
    private CarServiceImpl carServiceImpl;

    @PostMapping("/create")
    public CarService create(@RequestBody CarService carService) {
        return this.carServiceImpl.create(carService);
    }

    @GetMapping("/getAll")
    public List<CarService> getAllServices() {
        return carServiceImpl.getAll();
    }

    @PostMapping ("/update/{id}")
    public CarService update(@RequestBody CarService carService) {
        return carServiceImpl.update(carService);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        carServiceImpl.delete(id);
    }
}
/*ServiceController.java
CarService Controller class
Thabiso Kama
25 May 2025
 */