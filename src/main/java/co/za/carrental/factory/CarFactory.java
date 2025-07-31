package co.za.carrental.factory;

import co.za.carrental.domain.Car;
import co.za.carrental.domain.CarType;
import co.za.carrental.domain.Status;

import java.math.BigDecimal;

public class CarFactory {

    public static Car createCar(String carId, String make, String model, int year, Status status, CarType carType, BigDecimal dailyRate) {
        // Ensure licensePlate is derived or passed explicitly
        String licensePlate = carId != null ? carId : "UNKNOWN";

        return Car.builder()
                .carId(carId)
                .make(make)
                .model(model)
                .year(year)
                .licensePlate(licensePlate)
                .status(status != null ? status.toString() : "UNKNOWN") // Handle null status
                .carType(carType)
                .dailyRate(dailyRate != null ? dailyRate : BigDecimal.ZERO) // Handle null dailyRate
                .build();
    }

    public static Car buildCar(String carId, String make, String model, int year, String licensePlate, CarType carType, BigDecimal dailyRate) {
        return Car.builder()
                .carId(carId)
                .make(make)
                .model(model)
                .year(year)
                .licensePlate(licensePlate != null ? licensePlate : "UNKNOWN") // Handle null licensePlate
                .status("AVAILABLE") // Default status
                .carType(carType)
                .dailyRate(dailyRate != null ? dailyRate : BigDecimal.ZERO) // Handle null dailyRate
                .build();
    }
}