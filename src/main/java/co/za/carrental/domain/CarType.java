package co.za.carrental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CarType {
    @Id
    private String typeId;
    private String name;
    private float dailyRate;
    private float lateFeePerHour;
    private int seatingCapacity;

    public CarType() {}

    private CarType(Builder builder) {
        this.typeId = builder.typeId;
        this.name = builder.name;
        this.dailyRate = builder.dailyRate;
        this.lateFeePerHour = builder.lateFeePerHour;
        this.seatingCapacity = builder.seatingCapacity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String typeId;
        private String name;
        private float dailyRate;
        private float lateFeePerHour;
        private int seatingCapacity;

        public Builder typeId(String typeId) {
            this.typeId = typeId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder dailyRate(float dailyRate) {
            this.dailyRate = dailyRate;
            return this;
        }

        public Builder lateFeePerHour(float lateFeePerHour) {
            this.lateFeePerHour = lateFeePerHour;
            return this;
        }

        public Builder seatingCapacity(int seatingCapacity) {
            this.seatingCapacity = seatingCapacity;
            return this;
        }

        public CarType build() {
            return new CarType(this);
        }
    }

    // Getters
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(float dailyRate) {
        this.dailyRate = dailyRate;
    }

    public float getLateFeePerHour() {
        return lateFeePerHour;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }
}