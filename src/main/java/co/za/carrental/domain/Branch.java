/*
 *
 * Author: Milani Ncana (216269369)
 * Date: 11 May 2025
 */

package co.za.carrental.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "branch")
public class Branch {

    @Id
    private String branchId;

    private String address;
    private String phone;

    // Default constructor required by JPA
    public Branch() {
    }

    // Builder-based constructor
    private Branch(Builder builder) {
        this.branchId = builder.branchId;
        this.address = builder.address;
        this.phone = builder.phone;
    }

    // --- Setters ---
    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // --- Getters ---
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

    // --- Builder ---
    public static class Builder {
        private String branchId;
        private String address;
        private String phone;

        public Builder setBranchId(String branchId) {
            this.branchId = branchId;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Branch build() {
            return new Branch(this);
        }
    }
}
