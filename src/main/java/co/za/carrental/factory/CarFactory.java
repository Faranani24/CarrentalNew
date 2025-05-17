package co.za.carrental.factory;

import co.za.carrental.domain.Car;
import co.za.carrental.domain.CarType;
import co.za.carrental.domain.Status;

public class CarFactory {
    public static Car createCar(String carId, String licensePlate, String make,
                                String model, int year, CarType carType) {
        return new Car.Builder()
                .setCarId(carId)
                .setLicensePlate(licensePlate)
                .setMake(make)
                .setModel(model)
                .setYear(year)
                .setStatus(Status.AVAILABLE) // Default status
                .setCarType(carType)
                .build();
    }
}