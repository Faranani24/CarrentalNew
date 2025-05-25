//Faranani Khangale
// Created on 2025/05/25
// 230136982
// This code is part of a car rental system,

package co.za.carrental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "car")
public class Car {

    private String carId;
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private Status status;
    private String carType;  // keep as String for DB storage

    @Id
    private Long id;


    public Car() {
    }

    private Car(Builder builder) {
        this.carId = builder.carId;
        this.licensePlate = builder.licensePlate;
        this.make = builder.make;
        this.model = builder.model;
        this.year = builder.year;
        this.status = builder.status;
        this.carType = builder.carType;
        this.id = builder.id;
    }

    // Getters
    public String getCarId() { return carId; }
    public String getLicensePlate() { return licensePlate; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public Status getStatus() { return status; }

    // Convert stored String to enum
    public CarType getCarType() {
        return carType == null ? null : CarType.valueOf(carType);
    }

    public Long getId() { return id; }

    // Setters
    public void setCarId(String carId) { this.carId = carId; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public void setMake(String make) { this.make = make; }
    public void setModel(String model) { this.model = model; }
    public void setYear(int year) { this.year = year; }
    public void setStatus(Status status) { this.status = status; }

    // Convert enum to String for storage
    public void setCarType(CarType carType) {
        this.carType = carType == null ? null : carType.name();
    }

    public void setId(Long id) { this.id = id; }

    public static class Builder {
        private String carId;
        private String licensePlate;
        private String make;
        private String model;
        private int year;
        private Status status;
        private String carType;  // store as String in builder too
        private Long id;

        public Builder setCarId(String carId) {
            this.carId = carId;
            return this;
        }

        public Builder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public Builder setMake(String make) {
            this.make = make;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setYear(int year) {
            this.year = year;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        // Accept CarType enum but store as String
        public Builder setCarType(CarType carType) {
            this.carType = carType == null ? null : carType.name();
            return this;
        }

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId='" + carId + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", status=" + status +
                ", carType=" + carType +
                ", id=" + id +
                '}';
    }
}
