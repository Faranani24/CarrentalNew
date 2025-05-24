/*
 * Customer.java
 * Author: Lance Anthony Franks (230803865)
 * Date: 11 May 2025
 */

package co.za.carrental.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
    private String licenseNumber;
    @ElementCollection
    private List<String> paymentMethods;

    protected Customer() {}

    private Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
        this.phone = builder.phone;
        this.licenseNumber = builder.licenseNumber;
        this.paymentMethods = builder.paymentMethods;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<String> getPaymentMethods() {
        return paymentMethods;
    }

    public static class Builder {
        private String customerId;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String phone;
        private String licenseNumber;
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
    public Builder setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
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

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", paymentMethods=" + paymentMethods +
                '}';
    }
}
