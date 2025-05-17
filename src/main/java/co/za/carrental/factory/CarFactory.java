package co.za.carrental.factory;

import co.za.carrental.domain.Car;
import co.za.carrental.domain.Status;

public class CarFactory {
    public static Car createCar(String carId, String licensePlate, String make, String model, String year) {
        return new Car.Builder()
                .setCarId(carId)
                .setLicensePlate(licensePlate)
                .setMake(make)
                .setModel(model)
                .setYear(Integer.parseInt(year))
                .setStatus(Status.AVAILABLE)
                .build();
    }
}
