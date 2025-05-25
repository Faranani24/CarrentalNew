package co.za.carrental.domain;

/* CarType class representing the type of car in the rental system
   CarType POJO class with Builder pattern
   Author: Faranani Khangale (230136982)
   Date: 2025-05-11
*/

public class CarType {

    private String typeId;
    private int seatingCapacity;
    private Float dailyRate;
    private Float lateFeePerHour;

    public CarType() {
    }

    public CarType(Builder builder) {
        this.typeId = builder.typeId;
        this.seatingCapacity = builder.seatingCapacity;
        this.dailyRate = builder.dailyRate;
        this.lateFeePerHour = builder.lateFeePerHour;
    }

    public CarType(String type1, String sedan) {
        this.typeId = type1;
        this.seatingCapacity = 5; // Default seating capacity for a sedan
        this.dailyRate = 150.0f; // Default daily rate for a sedan
        this.lateFeePerHour = 15.0f; // Default late fee per hour for a sedan
    }

    public String getTypeId() { return typeId; }
    public int getSeatingCapacity() { return seatingCapacity; }
    public Float getDailyRate() { return dailyRate; }
    public Float getLateFeePerHour() { return lateFeePerHour; }

    // Over here i added the Static predefined CarType instances for common types
    public static final CarType SEDAN = new CarType.Builder()
            .setTypeId("SEDAN")
            .setSeatingCapacity(5)
            .setDailyRate(150.0f)
            .setLateFeePerHour(15.0f)
            .build();

    public static final CarType SUV = new CarType.Builder()
            .setTypeId("SUV")
            .setSeatingCapacity(7)
            .setDailyRate(200.0f)
            .setLateFeePerHour(20.0f)
            .build();

    public static class Builder {
        private String typeId;
        private int seatingCapacity;
        private Float dailyRate;
        private Float lateFeePerHour;

        public Builder setTypeId(String typeId) {
            this.typeId = typeId;
            return this;
        }

        public Builder setSeatingCapacity(int seatingCapacity) {
            this.seatingCapacity = seatingCapacity;
            return this;
        }

        public Builder setDailyRate(Float dailyRate) {
            this.dailyRate = dailyRate;
            return this;
        }

        public Builder setLateFeePerHour(Float lateFeePerHour) {
            this.lateFeePerHour = lateFeePerHour;
            return this;
        }

        public CarType build() {
            return new CarType(this);
        }

        @Override
        public String toString() {
            return "CarType{" +
                    "typeId='" + typeId + '\'' +
                    ", seatingCapacity=" + seatingCapacity +
                    ", dailyRate=" + dailyRate +
                    ", lateFeePerHour=" + lateFeePerHour +
                    '}';
        }

        public static Builder fromCarType(CarType carType) {
            return new Builder()
                    .setTypeId(carType.getTypeId())
                    .setSeatingCapacity(carType.getSeatingCapacity())
                    .setDailyRate(carType.getDailyRate())
                    .setLateFeePerHour(carType.getLateFeePerHour());
        }
    }
}
