package co.za.carrental.domain;

public class Admin {
    private  String adminId;
    private  String email;
    private  String password;

    private Admin(Builder builder) {
        this.adminId = builder.adminId;
        this.email = builder.email;
        this.password = builder.password;
    }

    public static class Builder {
        private String adminId;
        private String email;
        private String password;


        public void setAdminId(String adminId) {
            this.adminId = adminId;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
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
/*Admin.java
Admin Entity class
Author: Thabiso Kama
Date: 11 May 2025
 */