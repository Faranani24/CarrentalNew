/*CarServiceImpl.java
CarService Entity Class
Author: Thabiso Kama
Date: 11 May 2025
 */

package co.za.carrental.service.impl;

import jakarta.persistence.*;
import java.util.Objects; // Import Objects for equals and hashCode

@Entity
@Table(name = "car_services")
public class CarService {
    @Id
    @Column(name = "service_id", nullable = false)
    private String serviceId; // Now String, so repository/service methods must adapt
    @Column(nullable = false)
    private String name;
    @Column(name = "cost_per_day", nullable = false)
    private float costPerDay;


    private CarService(Builder builder) {
        this.serviceId = builder.serviceId;
        this.name = builder.name;
        this.costPerDay = builder.costPerDay;
    }

    // JPA no-arg constructor - typically protected if using builder exclusively
    protected CarService() {
    }


    public String getServiceId() {
        return serviceId;
    }

    public String getName() {
        return name;
    }

    public float getCostPerDay() {
        return costPerDay;
    }


    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCostPerDay(float costPerDay) {
        this.costPerDay = costPerDay;
    }



    @Override
    public String toString() {
        return "CarService{" +
                "serviceId='" + serviceId + '\'' +
                ", name='" + name + '\'' +
                ", costPerDay=" + costPerDay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarService that = (CarService) o;
        return Objects.equals(serviceId, that.serviceId); // Equality based on ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId); // Hash based on ID
    }


    public static class Builder {
        private String serviceId;
        private String name;
        private float costPerDay;

        // Builder methods for each field
        public Builder setServiceId(String serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCostPerDay(float costPerDay) {
            this.costPerDay = costPerDay;
            return this;
        }

        public CarService build() {
            // Optional: Add validation here before building
            return new CarService(this);
        }

        // It's generally better to have toString on the main entity class for debugging,
        // but having it on the Builder can be useful during the build process.
        @Override
        public String toString() {
            return "Builder{" +
                    "serviceId='" + serviceId + '\'' +
                    ", name='" + name + '\'' +
                    ", costPerDay=" + costPerDay +
                    '}';
        }
    }
}