package co.za.carrental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import java.util.Objects;

@Entity
public class CarType {
    @Id
    @NotBlank
    @Size(max = 50)
    @Pattern(
            regexp = "^(T\\d{3}|CT\\d{3}|default-type-id)$",
            message = "typeId must be T###, CT###, or default-type-id"
    )
    private String typeId;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Positive
    private float dailyRate;

    @PositiveOrZero
    private float lateFeePerHour;

    @Min(1)
    private int seatingCapacity;

    public CarType() {}

    private CarType(Builder builder) {
        this.typeId = builder.typeId;
        this.name = builder.name;
        this.dailyRate = builder.dailyRate;
        this.lateFeePerHour = builder.lateFeePerHour;
        this.seatingCapacity = builder.seatingCapacity;
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String typeId;
        private String name;
        private float dailyRate;
        private float lateFeePerHour;
        private int seatingCapacity;

        public Builder typeId(String typeId) { this.typeId = typeId; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder dailyRate(float dailyRate) { this.dailyRate = dailyRate; return this; }
        public Builder lateFeePerHour(float lateFeePerHour) { this.lateFeePerHour = lateFeePerHour; return this; }
        public Builder seatingCapacity(int seatingCapacity) { this.seatingCapacity = seatingCapacity; return this; }
        public CarType build() { return new CarType(this); }
    }

    public String getTypeId() { return typeId; }
    public void setTypeId(String typeId) { this.typeId = typeId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public float getDailyRate() { return dailyRate; }
    public void setDailyRate(float dailyRate) { this.dailyRate = dailyRate; }
    public float getLateFeePerHour() { return lateFeePerHour; }
    public void setLateFeePerHour(float lateFeePerHour) { this.lateFeePerHour = lateFeePerHour; }
    public int getSeatingCapacity() { return seatingCapacity; }
    public void setSeatingCapacity(int seatingCapacity) { this.seatingCapacity = seatingCapacity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarType)) return false;
        CarType carType = (CarType) o;
        return Objects.equals(typeId, carType.typeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId);
    }

    @Override
    public String toString() {
        return "CarType{" +
                "typeId='" + typeId + '\'' +
                ", name='" + name + '\'' +
                ", dailyRate=" + dailyRate +
                ", lateFeePerHour=" + lateFeePerHour +
                ", seatingCapacity=" + seatingCapacity +
                '}';
    }
}