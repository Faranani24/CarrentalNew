package co.za.carrental.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import jakarta.persistence.Index;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "car", indexes = {
    @Index(name = "idx_car_carId", columnList = "carId")
})
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Internal DB PK

    @Column(unique = true, nullable = false, length = 64)
    private String carId; // Business key, must be unique

    private String make;
    private String model;
    private int year;
    private String licensePlate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private CarType carType;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal dailyRate;

    @Lob
    @Column(name = "image", columnDefinition="LONGBLOB")
    @JsonIgnore
    private byte[] image;

    // Cascade Bookings so deleting a car deletes associated bookings
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Booking> bookings;

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

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String carId;
        private String make;
        private String model;
        private int year;
        private String licensePlate;
        private Status status;
        private CarType carType;
        private BigDecimal dailyRate;
        private byte[] image;

        public Builder carId(String carId) { this.carId = carId; return this; }
        public Builder make(String make) { this.make = make; return this; }
        public Builder model(String model) { this.model = model; return this; }
        public Builder year(int year) { this.year = year; return this; }
        public Builder licensePlate(String licensePlate) { this.licensePlate = licensePlate; return this; }
        public Builder status(Status status) { this.status = status; return this; }
        public Builder carType(CarType carType) { this.carType = carType; return this; }
        public Builder dailyRate(BigDecimal dailyRate) { this.dailyRate = dailyRate; return this; }
        public Builder image(byte[] image) { this.image = image; return this; }
        public Car build() { return new Car(this); }
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCarId() { return carId; }
    public void setCarId(String carId) { this.carId = carId; }
    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public CarType getCarType() { return carType; }
    public void setCarType(CarType carType) { this.carType = carType; }
    public BigDecimal getDailyRate() { return dailyRate; }
    public void setDailyRate(BigDecimal dailyRate) { this.dailyRate = dailyRate; }
    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }
}
