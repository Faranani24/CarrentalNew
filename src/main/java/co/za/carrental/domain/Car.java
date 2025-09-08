package co.za.carrental.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String carId;
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private String status;

    @ManyToOne
    private CarType carType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyRate;

    @Lob
    @Column(name = "image", columnDefinition="LONGBLOB")
    @JsonIgnore // This annotation prevents the image from being serialized in standard JSON responses
    private byte[] image;

    public Car() {}

    private Car(Builder builder) {
        this.carId = builder.carId;
        this.make = builder.make;
        this.model = builder.model;
        this.year = builder.year;
        this.licensePlate = builder.licensePlate;
        this.status = builder.status;
        this.carType = builder.carType;
        this.dailyRate = builder.dailyRate;
        this.image = builder.image;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String carId;
        private String make;
        private String model;
        private int year;
        private String licensePlate;
        private String status;
        private CarType carType;
        private BigDecimal dailyRate;
        private byte[] image;

        public Builder carId(String carId) { this.carId = carId; return this; }
        public Builder make(String make) { this.make = make; return this; }
        public Builder model(String model) { this.model = model; return this; }
        public Builder year(int year) { this.year = year; return this; }
        public Builder licensePlate(String licensePlate) { this.licensePlate = licensePlate; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder carType(CarType carType) { this.carType = carType; return this; }
        public Builder dailyRate(BigDecimal dailyRate) { this.dailyRate = dailyRate; return this; }
        public Builder image(byte[] image) { this.image = image; return this; }
        public Car build() { return new Car(this); }
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public CarType getCarType() { return carType; }
    public void setCarType(CarType carType) { this.carType = carType; }
    public BigDecimal getDailyRate() { return dailyRate; }
    public void setDailyRate(BigDecimal dailyRate) { this.dailyRate = dailyRate; }

    // The @JsonIgnore annotation is added to the getter to prevent the image from being serialized with the rest of the car data.
    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
