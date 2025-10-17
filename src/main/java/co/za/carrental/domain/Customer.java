package co.za.carrental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    @JsonIgnore  // Don't send password to frontend
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "license")
    private String license;

    @Column(name = "role")
    private String role = "CUSTOMER"; // Default role

    // Default constructor for JPA
    public Customer() {}

    private Customer(Builder builder) {
        this.customerId = builder.customerId;
        this.firstName = builder.firstName != null ? builder.firstName : "";
        this.lastName = builder.lastName != null ? builder.lastName : "";
        this.email = builder.email;
        this.password = builder.password;
        this.phone = builder.phone != null ? builder.phone : "";
        this.license = builder.license != null ? builder.license : "";
        this.role = builder.role != null ? builder.role : "CUSTOMER";
    }

    // Getters
    public String getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getLicense() {
        return license;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", license='" + license + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public static class Builder {
        private String customerId;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String phone;
        private String license;
        private String role = "CUSTOMER";

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

        public Builder setLicense(String license) {
            this.license = license;
            return this;
        }

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public Customer build() {
            if (customerId == null || customerId.trim().isEmpty()) {
                throw new IllegalStateException("Customer ID is required");
            }
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalStateException("Email is required");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalStateException("Password is required");
            }
            return new Customer(this);
        }
    }
}