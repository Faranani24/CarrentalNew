/*Admin.java
Admin Entity class
Author: Thabiso Kama
Date: 11 May 2025
 */

package co.za.carrental.domain;
import jakarta.persistence.*;

@Entity
@Table

public class Admin {
    @Id
    @Column(name = "admin_id", nullable = false)
    private  String adminId;
    @Column(nullable = false, unique = true)
    private  String email;
    @Column(nullable = false)
    private  String password;

    private Admin(Builder builder) {
        this.adminId = builder.adminId;
        this.email = builder.email;
        this.password = builder.password;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

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
            return new Admin(this);
        }
    }
        public void addCar(){
            System.out.println("Car added");
        }
        public void updateCar(){
            System.out.println("Car updated");
        }
        public void deleteCar(){
            System.out.println("Car deleted");
        }
        public void manageBookings(){
            System.out.println("Bookings managed");
        }
        @Override
        public String toString() {
            return "Admin: "+email;
    }
}
