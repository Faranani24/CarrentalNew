/*CarServiceImpl.java
CarService Entity Class
Author: Thabiso Kama
Date: 11 May 2025
 */

package co.za.carrental.domain;
import jakarta.persistence.*;

@Entity
@Table(name = "car_services")

public class CarService {
    @Id
    @Column(name = "service_id", nullable = false)
    private Integer serviceId;
    @Column(nullable = false)
    private String name;
    @Column(name = "cost_per_day", nullable = false)
    private float costPerDay;

    private CarService(Builder builder){
        this.serviceId = builder.serviceId;
        this.name = builder.name;
        this.costPerDay = builder.costPerDay;
    }

    public CarService() {

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

    public static class Builder{
        private String serviceId;
        private String name;
        private float costPerDay;

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

        public CarService build(){
            return new CarService(this);
        }

        @Override
        public String toString(){
            return name + "(R "+ costPerDay + " per day)";
        }
    }
}

