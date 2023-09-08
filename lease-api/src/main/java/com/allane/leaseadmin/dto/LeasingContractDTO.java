package com.allane.leaseadmin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record LeasingContractDTO(
        @NotNull(message = "Monthly rate is required")
        BigDecimal monthlyRate,
        @NotNull(message = "CustomerId is required")
        Long customerId,
        @NotNull(message = "VehicleId is required")
        Long vehicleId
) {}
