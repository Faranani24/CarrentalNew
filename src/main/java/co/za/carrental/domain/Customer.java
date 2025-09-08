// src/main/java/co/za/carrental/domain/Customer.java
package co.za.carrental.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    private String customerId;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String license; // <-- Add this field

    @ElementCollection
    private List<String> paymentMethods;

    public Customer() {}

    private Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.phone = builder.phone;
        this.license = builder.license; // <-- Assign license
        this.paymentMethods = builder.paymentMethods;
    }

    // --- Getters ---
    public String getCustomerId() { return customerId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getPhone() { return phone; }
    public String getLicense() { return license; } // <-- Add getter
    public List<String> getPaymentMethods() { return paymentMethods; }

    // --- Setters ---
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setLicense(String license) { this.license = license; } // <-- Add setter
    public void setPaymentMethods(List<String> paymentMethods) { this.paymentMethods = paymentMethods; }

    // --- Builder ---
    public static class Builder {
        private String customerId;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String phone;
        private String license; // <-- Add builder field
        private List<String> paymentMethods;

        public Builder setCustomerId(String customerId) {
            this.customerId = customerId;
            return this;
        }
        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }
        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }
        public Builder setLicense(String license) { // <-- Add builder method
            this.license = license;
            return this;
        }
        public Builder setPaymentMethods(List<String> paymentMethods) {
            this.paymentMethods = paymentMethods;
            return this;
        }
        public Customer build() {
            return new Customer(this);
        }
    }
}