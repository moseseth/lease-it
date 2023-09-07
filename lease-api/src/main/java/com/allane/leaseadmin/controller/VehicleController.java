package com.allane.leaseadmin.controller;


import com.allane.leaseadmin.dto.VehicleDTO;
import com.allane.leaseadmin.model.Vehicle;
import com.allane.leaseadmin.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        Vehicle createdVehicle = vehicleService.createVehicle(vehicleDTO);
        return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
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
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @Valid @RequestBody VehicleDTO vehicleDTO) {
        Vehicle updated = vehicleService.updateVehicle(id, vehicleDTO);
        return ResponseEntity.ok(updated);
    }
}
