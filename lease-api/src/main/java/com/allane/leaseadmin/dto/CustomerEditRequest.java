package com.allane.leaseadmin.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
public record CustomerEditRequest(String firstName, String lastName, String birthdate) {}
