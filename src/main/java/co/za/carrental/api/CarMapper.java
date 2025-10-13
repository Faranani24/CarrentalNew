package co.za.carrental.api;

import co.za.carrental.api.dto.CarDto;
import co.za.carrental.domain.Car;
import co.za.carrental.domain.CarType;

public final class CarMapper {
    private CarMapper() {}

    public static CarDto toDto(Car c) {
        CarType t = c.getCarType();
        return new CarDto(
                c.getId(),
                c.getCarId(),
                c.getMake(),
                c.getModel(),
                c.getYear(),
                c.getLicensePlate(),
                c.getStatus() != null ? c.getStatus().name() : null, // convert enum -> string
                t != null ? t.getTypeId() : null,
                t != null ? t.getName() : null,
                c.getDailyRate()
        );
    }


}