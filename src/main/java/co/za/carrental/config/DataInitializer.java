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

    // This Bean is responsible for initializing the database with sample data.
    // To prevent it from running on every startup, we will comment out the
    // data saving logic.
    @Bean
    CommandLineRunner initCars(CarRepository carRepository, CarTypeRepository carTypeRepository) {
        return args -> {
            /*
            // The following code is for initial data seeding.
            // It creates a CarType and a Car and saves them to the database.
            // Commenting this out prevents the data from being re-inserted on every application restart.
            CarType suvType = new CarType();
            suvType.setTypeId("T001");
            suvType.setName("SUV");
            suvType.setDailyRate(200.0f);
            suvType.setLateFeePerHour(50.0f);
            suvType.setSeatingCapacity(5);
            carTypeRepository.save(suvType);

            Car car = Car.builder()
                    .carId("54321")
                    .make("BMW")
                    .model("X5")
                    .year(2024)
                    .licensePlate("BMW543")
                    .status("AVAILABLE")
                    .carType(suvType)
                    .dailyRate(new BigDecimal("200.00"))
                    .imageUrl("https://example.com/images/bmw-x5.jpg")
                    .build();

            carRepository.save(car);
            */
            System.out.println("Data initialization bean is active, but no data will be inserted.");
        };
    }
}
