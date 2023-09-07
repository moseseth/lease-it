package com.allane.leaseadmin.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
public record CustomerDTO(String firstName, String lastName) {}
