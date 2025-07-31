package co.za.carrental.factory;

import co.za.carrental.domain.CarType;

public class CarTypeFactory {
    public static CarType createCarType(String typeId, String name, float dailyRate, float lateFeePerHour, int seatingCapacity) {
        return CarType.builder()
                .typeId(typeId)
                .name(name)
                .dailyRate(dailyRate)
                .lateFeePerHour(lateFeePerHour)
                .seatingCapacity(seatingCapacity)
                .build();
    }

    public static CarType createDefaultCarType() {
        return CarType.builder()
                .typeId("default-type-id")
                .name("Default Car Type")
                .dailyRate(100.0f)
                .lateFeePerHour(20.0f)
                .seatingCapacity(4)
                .build();
    }
}