package co.za.carrental.api.dto;

import java.math.BigDecimal;

public record CarDto(
        Long id,
        String carId,
        String make,
        String model,
        int year,
        String licensePlate,
        String status,
        String carTypeId,
        String carTypeName,
        BigDecimal dailyRate
) {}