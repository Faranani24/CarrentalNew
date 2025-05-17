package co.za.carrental.factory;

import co.za.carrental.domain.Car;

public class CarFactory {
    public static Car createCar(String carNumber, String carBrand, String carModel, String carColour, String carYear) {
                return new Car.Builder()
                .setCarId(carNumber)
                .setLicensePlate(carNumber)
                .setMake(carBrand)
                .setModel(carModel)
                .setYear(Integer.parseInt(carYear))
                .setStatus(co.za.carrental.domain.Status.AVAILABLE)
                        .build();
    }
}

