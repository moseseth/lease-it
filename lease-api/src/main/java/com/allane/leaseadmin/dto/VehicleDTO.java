package com.allane.leaseadmin.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public record VehicleDTO(
        String brand,
        String model,
        Integer modelYear,
        String vehicleIdentificationNumber,
        BigDecimal price
) {}
