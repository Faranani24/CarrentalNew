package co.za.carrental.factory;

import co.za.carrental.domain.CarType;

public class CarTypeFactory {
    public static CarType createCarType(String typeId, int seatingCapacity, Float dailyRate, Float lateFeePerHour) {
        return new CarType.Builder()
                .setTypeId(typeId)
                .setSeatingCapacity(seatingCapacity)
                .setDailyRate(dailyRate)
                .setLateFeePerHour(lateFeePerHour)
                .build();
    }
}
