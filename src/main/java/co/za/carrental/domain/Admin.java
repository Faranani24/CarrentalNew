package co.za.carrental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Admin {

    @Id
    private String adminId;
    private String email;
    private String password;

    protected Admin() {} // JPA requires no-arg constructor

    private Admin(Builder builder) {
        this.adminId = builder.adminId;
        this.email = builder.email;
        this.password = builder.password;
    }

    public String getAdminId() { return adminId; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public static class Builder {
        private String adminId;
        private String email;
        private String password;

        public Builder setAdminId(String adminId) {
            this.adminId = adminId;
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

        public Admin build() {
            return new
