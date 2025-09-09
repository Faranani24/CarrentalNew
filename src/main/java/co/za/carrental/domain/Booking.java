/*
 * Booking.java
 * Author: Lance Anthony Franks (230803865)
 * Date: 11 May 2025
 */

package co.za.carrental.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "booking")
public class Booking {


    @Id
    private String bookingId;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    private Float totalCost;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Add vehicle ID to link to your car data
    @Column(name = "car_id")
    private String carId; // This will store the car ID from your mock data

    // Constructor for the Builder pattern
    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.totalCost = builder.totalCost;
        this.status = builder.status;
        this.customer = builder.customer;
        this.carId = builder.vehicleId;
    }

    // Default constructor required by JPA
    public Booking() {
    }

    // --- Setters ---
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCarIdId(String vehicleId) {
        this.carId = vehicleId;
    }

    // --- Getters ---
    public String getBookingId() {
        return bookingId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getCarId() {
        return carId;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalCost=" + totalCost +
                ", status=" + status +
                ", customerId=" + (customer != null ? customer.getCustomerId() : "null") +
                ", vehicleId='" + carId + '\'' +
                '}';
    }

    // --- Builder Class ---
    public static class Builder {
        private String bookingId;
        private Date startDate;
        private Date endDate;
        private Float totalCost;
        private BookingStatus status;
        private Customer customer;
        private String vehicleId;


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

        public Builder setStatus(BookingStatus status) {
            this.status = status;
            return this;
        }

        public Builder setCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder setVehicleId(String vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}