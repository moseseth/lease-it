package com.allane.leaseadmin.controller;


import com.allane.leaseadmin.dto.ValidationErrorResponse;
import com.allane.leaseadmin.dto.VehicleDTO;
import com.allane.leaseadmin.model.Vehicle;
import com.allane.leaseadmin.service.VehicleService;
import com.allane.leaseadmin.util.ValidationHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicles")
@Tag(name = "Vehicles", description = "API endpoints for managing vehicle information")
public class VehicleController {
    private final VehicleService vehicleService;

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<?> createVehicle(@Valid @RequestBody Vehicle vehicle, BindingResult bindingResult) {
        List<ValidationErrorResponse> validationErrorsResponse = ValidationHelper.handleValidationErrors(bindingResult);
        if (validationErrorsResponse != null) {
            logger.warn("Validation errors occurred while creating a vehicle: {}", validationErrorsResponse);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorsResponse);
        }

        Vehicle createdVehicle = vehicleService.createVehicle(vehicle);
        logger.info("Created a new vehicle with ID: {}", createdVehicle.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdVehicle);
    }

    @GetMapping
    public List<VehicleDTO> getAllVehicles() {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();

        return vehicles.stream()
                .map(vehicle -> VehicleDTO.builder()
                        .brand(vehicle.getBrand())
                        .model(vehicle.getModel())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        Vehicle vehicle = vehicleService.getVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleDTO vehicleDTO) {
        Vehicle updated = vehicleService.updateVehicle(id, vehicleDTO);
        return ResponseEntity.ok(updated);
    }
}
