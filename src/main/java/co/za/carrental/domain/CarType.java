package co.za.carrental.domain;

public class CarType {
    private String typeId;

    private int seatingCapacity;
    private Float dailyRate;
    private Float lateFeePerHour;

    private CarType(Builder builder) {
        this.typeId = builder.typeId;
        this.seatingCapacity = builder.seatingCapacity;
        this.dailyRate = builder.dailyRate;
        this.lateFeePerHour = builder.lateFeePerHour;
    }


    public String getTypeId() {
        return typeId;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public Float getDailyRate() {
        return dailyRate;
    }

    public Float getLateFeePerHour() {
        return lateFeePerHour;
    }
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
            return "Builder{" +
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
