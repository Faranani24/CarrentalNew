package co.za.carrental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Service {

    @Id
    private String serviceId;
    private String name;
    private float costPerDay;

    protected Service() {}

    private Service(Builder builder) {
        this.serviceId = builder.serviceId;
        this.name = builder.name;
        this.costPerDay = builder.costPerDay;
    }

    public String getServiceId() { return serviceId; }
    public String getName() { return name; }
    public float getCostPerDay() { return costPerDay; }

    public static class Builder {
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

        public Service build() {
            return new Service(this);
        }
    }

    @Override
    public String toString() {
        return name + " (R" + costPerDay + " per day)";
    }
  }
