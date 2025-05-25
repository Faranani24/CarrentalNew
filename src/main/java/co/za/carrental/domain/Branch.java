package co.za.carrental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Branch.java
 * Branch entity class for car rental system
 * Author: Milani Ncana (216269369)
 * Date: [11/05/2025]
 */
@Entity
public class Branch {
    private final String branchId;
    private final String address;
    private final String phone;

    private Branch(Builder builder) {
        this.branchId = builder.branchId;
        this.address = builder.address;
        this.phone = builder.phone;
    }

    // Builder Class
    public static class Builder {
        private String branchId;
        private String address;
        private String phone;

        @Id
        public Builder branchId(String branchId) {
            this.branchId = branchId;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Branch build() {
            return new Branch(this);
        }
    }

    // Getters
    public String getBranchId() {
        return branchId;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchId='" + branchId + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
