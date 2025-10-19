package co.za.carrental.factory;

import co.za.carrental.domain.Car;
import co.za.carrental.domain.CarType;
import co.za.carrental.domain.Status;

import java.math.BigDecimal;

public class CarFactory {

    public static Car createCar(String carId, String make, String model, int year, Status status, CarType carType, BigDecimal dailyRate, String description) {

        String licensePlate = carId != null ? carId : "UNKNOWN";

        return Car.builder()
                .carId(carId)
                .make(make)
                .model(model)
                .year(year)
                .licensePlate(licensePlate)
                .description(description)  // ADD THIS
                .status(status != null ? status : Status.AVAILABLE)
                .carType(carType)
                .dailyRate(dailyRate != null ? dailyRate : BigDecimal.ZERO)
                .build();
    }


    public static Car buildCar(String carId, String make, String model, int year, String licensePlate, CarType carType, BigDecimal dailyRate, String description) {
        return Car.builder()
                .carId(carId)
                .make(make)
                .model(model)
                .year(year)
                .licensePlate(licensePlate != null ? licensePlate : "UNKNOWN")
                .description(description)  // ADD THIS
                .status(Status.AVAILABLE)
                .carType(carType)
                .dailyRate(dailyRate != null ? dailyRate : BigDecimal.ZERO)
                .build();
    }


    public static Car createCar(String carId, String make, String model, int year, Status status, CarType carType, BigDecimal dailyRate) {
        return createCar(carId, make, model, year, status, carType, dailyRate, null);
    }


    public static Car buildCar(String carId, String make, String model, int year, String licensePlate, CarType carType, BigDecimal dailyRate) {
        return buildCar(carId, make, model, year, licensePlate, carType, dailyRate, null);
    }
}