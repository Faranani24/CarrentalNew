package co.za.carrental.domain;

/* Car class representing a car in the rental system
   Car POJO class with Builder pattern
   Author: Faranani Khangale (230136982)
   Date: 2025-05-11
*/
public class Car {
    private final String carId;
    private final String licensePlate;
    private final String make;
    private final String model;
    private final int year;
    private final Status status;
    private final CarType carType;

    private Car(Builder builder) {
        this.carId = builder.carId;
        this.licensePlate = builder.licensePlate;
        this.make = builder.make;
        this.model = builder.model;
        this.year = builder.year;
        this.status = builder.status;
        this.carType = builder.carType;
    }

    // Getters
    public String getCarId() {
        return carId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public Status getStatus() {
        return status;
    }

    public CarType getCarType() {
        return carType;
    }

    public String getId() {
        return carId;
    }

    public static class Builder {
        private String carId;
        private String licensePlate;
        private String make;
        private String model;
        private int year;
        private Status status;
        private CarType carType;

        public Builder(String car001, String toyota, String corolla, int i, String sedan, float v) {
        }

        public Builder() {

        }

        public Builder(String car001, String toyota, String corolla, int i) {
        }


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

        public Builder setCarType(CarType carType) {
            this.carType = carType;
            return this;
        }


        public Car build() {
            return new Car(this);
        }


        @Override
        public String toString() {
            return "Builder{" +
                    "carId='" + carId + '\'' +
                    ", licensePlate='" + licensePlate + '\'' +
                    ", make='" + make + '\'' +
                    ", model='" + model + '\'' +
                    ", year=" + year +
                    ", status=" + status +
                    ", carType=" + carType +
                    '}';
        }


        public static Builder fromCar(Car car) {
            return new Builder()
                    .setCarId(car.getCarId())
                    .setLicensePlate(car.getLicensePlate())
                    .setMake(car.getMake())
                    .setModel(car.getModel())
                    .setYear(car.getYear())
                    .setStatus(car.getStatus())
                    .setCarType(car.getCarType());
        }
    }
}