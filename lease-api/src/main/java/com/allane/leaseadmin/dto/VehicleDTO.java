package com.allane.leaseadmin.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record VehicleDTO(
        String brand,
        String model,

        Integer modelYear,
        String vehicleIdentificationNumber,
        BigDecimal price
) {}
