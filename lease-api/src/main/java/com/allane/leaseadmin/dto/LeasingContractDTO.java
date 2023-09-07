package com.allane.leaseadmin.dto;

import com.allane.leaseadmin.model.Customer;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
public record LeasingContractDTO(
        @NotNull(message = "Contract number is required")
        Integer contractNumber,
        @NotNull(message = "Monthly rate is required")
        BigDecimal monthlyRate,
        @NotNull(message = "Customer is required")
        Long customerId,
        @NotNull(message = "Vehicle is required")
        Long vehicleId
) {}
