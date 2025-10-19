package co.za.carrental.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    private String bookingId;

    private LocalDate startDate;

    private LocalDate endDate;

    private Float totalCost;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    @JsonIgnoreProperties({"password", "bookings"})
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id", referencedColumnName = "carId")
    @JsonIgnoreProperties({"image", "bookings"})
    private Car car;

    public Booking() {}

    private Booking(Builder b) {
        this.bookingId = b.bookingId;
        this.startDate = b.startDate;
        this.endDate = b.endDate;
        this.totalCost = b.totalCost;
        this.status = b.status;
        this.customer = b.customer;
        this.car = b.car;
    }


    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Float getTotalCost() { return totalCost; }
    public void setTotalCost(Float totalCost) { this.totalCost = totalCost; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }

    public static class Builder {
        private String bookingId;
        private LocalDate startDate;
        private LocalDate endDate;
        private Float totalCost;
        private BookingStatus status;
        private Customer customer;
        private Car car;

        public Builder setBookingId(String bookingId) { this.bookingId = bookingId; return this; }
        public Builder setStartDate(LocalDate startDate) { this.startDate = startDate; return this; }
        public Builder setEndDate(LocalDate endDate) { this.endDate = endDate; return this; }
        public Builder setTotalCost(Float totalCost) { this.totalCost = totalCost; return this; }
        public Builder setStatus(BookingStatus status) { this.status = status; return this; }
        public Builder setCustomer(Customer customer) { this.customer = customer; return this; }
        public Builder setCar(Car car) { this.car = car; return this; }
        public Booking build() { return new Booking(this); }
    }
}