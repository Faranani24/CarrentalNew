package co.za.carrental.config;

import co.za.carrental.domain.Car;
import co.za.carrental.domain.CarType;
import co.za.carrental.repository.CarRepository;
import co.za.carrental.repository.CarTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {




    @Bean
    CommandLineRunner initCars(CarRepository carRepository, CarTypeRepository carTypeRepository) {
        return args -> {

            System.out.println("Data initialization bean is active, but no data will be inserted.");
        };
    }
}
