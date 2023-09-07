package com.allane.leaseadmin.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ContractOverviewDTO (
    String contractNumber,
    String customerSummary,
    String vehicleBrand,
    String vehicleModel,
    Integer vehicleModelYear,
    String vehicleVin,
    BigDecimal monthlyRate,
    BigDecimal vehiclePrice,
    String contractDetailsLink
) {}
