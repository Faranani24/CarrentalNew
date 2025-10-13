package co.za.carrental.factory;

import co.za.carrental.domain.Car;
import co.za.carrental.domain.CarType;
import co.za.carrental.domain.Status;

import java.math.BigDecimal;

public class CarFactory {

    public static Car createCar(String carId, String make, String model, int year, Status status, CarType carType, BigDecimal dailyRate) {
        // If licensePlate is not provided, derive from carId
        String licensePlate = carId != null ? carId : "UNKNOWN";

        return Car.builder()
                .carId(carId)
                .make(make)
                .model(model)
                .year(year)
                .licensePlate(licensePlate)
                .status(status != null ? status : Status.AVAILABLE) // Enum directly
                .carType(carType)
                .dailyRate(dailyRate != null ? dailyRate : BigDecimal.ZERO)
                .build();
    }

    // Optional: simple builder for default car
    public static Car buildCar(String carId, String make, String model, int year, String licensePlate, CarType carType, BigDecimal dailyRate) {
        return Car.builder()
                .carId(carId)
                .make(make)
                .model(model)
                .year(year)
                .licensePlate(licensePlate != null ? licensePlate : "UNKNOWN")
                .status(Status.AVAILABLE) // Default status
                .carType(carType)
                .dailyRate(dailyRate != null ? dailyRate : BigDecimal.ZERO)
                .build();
    }
}
