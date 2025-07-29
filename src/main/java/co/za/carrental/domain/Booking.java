/*
 * Booking.java
 * Author: Lance Anthony Franks (230803865)
 * Date: 11 May 2025
 */

package co.za.carrental.domain;

import jakarta.persistence.*;
import java.time.LocalDate; // Preferred for date-only fields
import java.util.Date; // Keep if specifically needed for existing methods/data

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    // Since you are generating UUIDs in the factory, you don't use @GeneratedValue
    // If the DB auto-generates UUIDs, you'd use: @GeneratedValue(strategy = GenerationType.UUID)
    private String bookingId;

    // It's generally better to use java.time.LocalDate for dates without time components in JPA
    // This avoids time zone issues and makes date comparisons simpler.
    // If you stick with java.util.Date, ensure consistent handling of timezones.
    private Date startDate; // Keep original as is for now, but LocalDate is preferred.
    private Date endDate;   // Keep original as is for now, but LocalDate is preferred.

    private Float totalCost;

    @Enumerated(EnumType.STRING) // Store enum as String in DB for readability
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id") // Specifies the foreign key column in the 'booking' table
    private Customer customer;

    // **** POTENTIAL ADDITION: If Booking also requires a Vehicle ****
    // If a Booking must always be associated with a Vehicle, add this:
    /*
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle; // You would need a Vehicle entity and repository
    */
    // ***************************************************************


    // Constructor for the Builder pattern
    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.totalCost = builder.totalCost;
        this.status = builder.status;
        this.customer = builder.customer;
        // if (builder.vehicle != null) this.vehicle = builder.vehicle; // For Vehicle
    }

    // Default constructor required by JPA
    public Booking() {
    }

    // --- Setters (adjust for LocalDate if you switch) ---
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public void setStartDate(Date startDate) { // Or LocalDate startDate
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {     // Or LocalDate endDate
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

    /*
    public void setVehicle(Vehicle vehicle) { // For Vehicle
        this.vehicle = vehicle;
    }
    */

    // --- Getters ---
    public String getBookingId() {
        return bookingId;
    }

    public Date getStartDate() { // Or LocalDate
        return startDate;
    }

    public Date getEndDate() { // Or LocalDate
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

    /*
    public Vehicle getVehicle() { // For Vehicle
        return vehicle;
    }
    */

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalCost=" + totalCost +
                ", status=" + status +
                ", customerId=" + (customer != null ? customer.getCustomerId() : "null") +
                // ", vehicleId=" + (vehicle != null ? vehicle.getVehicleId() : "null") + // For Vehicle
                '}';
    }

    // --- Builder Class (update to include customer and potentially vehicle) ---
    public static class Builder {
        private String bookingId;
        private Date startDate;
        private Date endDate;
        private Float totalCost;
        private BookingStatus status;
        private Customer customer;
        // private Vehicle vehicle; // For Vehicle

        public Builder setBookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setStartDate(Date startDate) { // Or LocalDate
            this.startDate = startDate;
            return this;
        }

        public Builder setEndDate(Date endDate) {     // Or LocalDate
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

        /*
        public Builder setVehicle(Vehicle vehicle) { // For Vehicle
            this.vehicle = vehicle;
            return this;
        }
        */

        public Booking build() {
            return new Booking(this);
        }
    }
}