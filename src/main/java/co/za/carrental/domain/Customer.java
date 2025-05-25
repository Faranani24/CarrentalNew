package co.za.carrental.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Customer {
    @Id
    private String customerId; // This is the field you want to keep
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String licenseNumber;

    @ElementCollection
    @CollectionTable(name = "customer_payment_methods", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "payment_method")
    private List<String> paymentMethods;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    // Private constructor to be used by the Builder
    private Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.phoneNumber = builder.phoneNumber;
        this.licenseNumber = builder.licenseNumber;
        this.paymentMethods = builder.paymentMethods;
        this.bookings = builder.bookings;
    }

    // JPA no-arg constructor
    protected Customer() {
        this.paymentMethods = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    // --- Getters and Setters ---
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getLicenseNumber() { return licenseNumber; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public List<String> getPaymentMethods() { return paymentMethods; }
    public void setPaymentMethods(List<String> paymentMethods) { this.paymentMethods = paymentMethods; }
    public List<Booking> getBookings() { return bookings; }
    public void setBookings(List<Booking> bookings) { this.bookings = bookings; }


    // --- Builder Class ---
    public static class Builder {
        private String customerId;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String phoneNumber;
        private String licenseNumber;
        private List<String> paymentMethods = new ArrayList<>();
        private List<Booking> bookings = new ArrayList<>();

        // Constructor for required fields
        public Builder(String customerId, String firstName, String lastName, String email) {
            this.customerId = customerId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        // Setter-like methods for optional fields, returning 'this' for chaining
        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder licenseNumber(String licenseNumber) {
            this.licenseNumber = licenseNumber;
            return this;
        }

        public Builder paymentMethods(List<String> paymentMethods) {
            this.paymentMethods = paymentMethods;
            return this;
        }

        public Builder bookings(List<Booking> bookings) {
            this.bookings = bookings;
            return this;
        }

        // Build method to create the Customer instance
        public Customer build() {
            // You can add validation here if needed
            return new Customer(this);
        }
    }

    // --- toString, equals, hashCode ---
    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", paymentMethods=" + paymentMethods +
                ", bookings=" + bookings +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}