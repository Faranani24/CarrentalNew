//Faranani Khangale
// Created on 2025/05/25
// 230136982
// This code is part of a car rental system,
// specifically the cartype entity for creating car objects.
package co.za.carrental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "car_type")
public class CarType {

    @Id
    private String typeId;
    private int seatingCapacity;
    private Float dailyRate;
    private Float lateFeePerHour;

    public CarType() {
    }

    public CarType(Builder builder) {
        this.typeId = builder.typeId;
        this.seatingCapacity = builder.seatingCapacity;
        this.dailyRate = builder.dailyRate;
        this.lateFeePerHour = builder.lateFeePerHour;
    }

    // Optionally keep or remove this constructor, but better to keep it for convenience
    public CarType(String type1, String sedan) {
        this.typeId = type1;
        this.seatingCapacity = 5;
        this.dailyRate = 150.0f;
        this.lateFeePerHour = 15.0f;
    }

    public static CarType valueOf(String carType) {
        switch (carType.toUpperCase()) {
            case "SEDAN":
                return SEDAN;
            case "SUV":
                return SUV;
            default:
                throw new IllegalArgumentException("Unknown car type: " + carType);
        }
    }

    public String getTypeId() { return typeId; }
    public int getSeatingCapacity() { return seatingCapacity; }
    public Float getDailyRate() { return dailyRate; }
    public Float getLateFeePerHour() { return lateFeePerHour; }

    public static final CarType SEDAN = new CarType.Builder()
            .setTypeId("SEDAN")
            .setSeatingCapacity(5)
            .setDailyRate(150.0f)
            .setLateFeePerHour(15.0f)
            .build();

    public static final CarType SUV = new CarType.Builder()
            .setTypeId("SUV")
            .setSeatingCapacity(7)
            .setDailyRate(200.0f)
            .setLateFeePerHour(20.0f)
            .build();

    public String name() {
        return typeId;
    }

    public static class Builder {
        private String typeId;
        private int seatingCapacity;
        private Float dailyRate;
        private Float lateFeePerHour;

        public Builder setTypeId(String typeId) {
            this.typeId = typeId;
            return this;
        }

        public Builder setSeatingCapacity(int seatingCapacity) {
            this.seatingCapacity = seatingCapacity;
            return this;
        }

        public Builder setDailyRate(Float dailyRate) {
            this.dailyRate = dailyRate;
            return this;
        }

        public Builder setLateFeePerHour(Float lateFeePerHour) {
            this.lateFeePerHour = lateFeePerHour;
            return this;
        }

        public CarType build() {
            return new CarType(this);
        }

        @Override
        public String toString() {
            return "CarType{" +
                    "typeId='" + typeId + '\'' +
                    ", seatingCapacity=" + seatingCapacity +
                    ", dailyRate=" + dailyRate +
                    ", lateFeePerHour=" + lateFeePerHour +
                    '}';
        }

        public static Builder fromCarType(CarType carType) {
            return new Builder()
                    .setTypeId(carType.getTypeId())
                    .setSeatingCapacity(carType.getSeatingCapacity())
                    .setDailyRate(carType.getDailyRate())
                    .setLateFeePerHour(carType.getLateFeePerHour());
        }
    }
}
