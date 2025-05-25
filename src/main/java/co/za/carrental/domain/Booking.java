package co.za.carrental.domain;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;


@Entity
public class Booking {
    @Id
    private String bookingId;
    private Date startDate;
    private Date endDate;
    private Float totalCost;
    private String carId; // <--- ADDED THIS FIELD

    @Enumerated(EnumType.STRING)
    private BookingStatus status;


    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Private constructor for the Builder
    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.totalCost = builder.totalCost;
        this.carId = builder.carId; // <--- ADDED TO BUILDER CONSTRUCTOR
        this.status = builder.status;
        this.customer = builder.customer;
    }

    // JPA no-arg constructor
    protected Booking() {}

    // Getters and Setters
    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public Float getTotalCost() { return totalCost; }
    public void setTotalCost(Float totalCost) { this.totalCost = totalCost; }
    public String getCarId() { return carId; } // <--- ADDED GETTER
    public void setCarId(String carId) { this.carId = carId; } // <--- ADDED SETTER
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    // --- Builder Class ---
    public static class Builder {
        private String bookingId;
        private Date startDate;
        private Date endDate;
        private Float totalCost;
        private String carId; // <--- ADDED THIS FIELD TO BUILDER
        private BookingStatus status;
        private Customer customer;

        public Builder setBookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }
        public Builder setStartDate(Date startDate) {
            this.startDate = startDate;
            return this;
        }
        public Builder setEndDate(Date endDate) {
            this.endDate = endDate;
            return this;
        }
        public Builder setTotalCost(Float totalCost) {
            this.totalCost = totalCost;
            return this;
        }
        public Builder setCarId(String carId) { // <--- ADDED TO BUILDER SETTER
            this.carId = carId;
            return this;
        }
        public Builder setStatus(BookingStatus status) {
            this.status = status;
            return this;
        }
        public Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Booking build() {
            // Basic validation:
            if (bookingId == null || startDate == null || endDate == null || totalCost == null || carId == null || status == null) { // <--- ADDED carId TO VALIDATION
                throw new IllegalArgumentException("Booking fields cannot be null during build.");
            }
            return new Booking(this);
        }
    }

    // toString, equals, hashCode (essential for entities)
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalCost=" + totalCost +
                ", carId='" + carId + '\'' + // <--- ADDED TO TOSTRING
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(bookingId, booking.bookingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId);
    }
}