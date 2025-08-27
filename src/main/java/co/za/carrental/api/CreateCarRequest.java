package co.za.carrental.api.request;

import co.za.carrental.domain.Status;
import java.math.BigDecimal;

public record CreateCarRequest(
        String carId,
        String make,
        String model,
        int year,
        String licensePlate,
        String carTypeId,
        Status status,
        BigDecimal overrideDailyRate
) {}