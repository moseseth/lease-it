package com.allane.leaseadmin.service;

import com.allane.leaseadmin.dto.VehicleDTO;
import com.allane.leaseadmin.exception.ResourceNotFoundException;
import com.allane.leaseadmin.model.Vehicle;
import com.allane.leaseadmin.repository.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public Vehicle createVehicle(@Valid VehicleDTO vehicleDTO) {
        // Create a new Vehicle instance from the DTO
        Vehicle newVehicle = Vehicle.builder()
                .brand(vehicleDTO.getBrand())
                .model(vehicleDTO.getModel())
                .modelYear(vehicleDTO.getModelYear())
                .vehicleIdentificationNumber(vehicleDTO.getVehicleIdentificationNumber())
                .price(vehicleDTO.getPrice())
                .build();

        // Save the new vehicle to the database
        return vehicleRepository.save(newVehicle);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id " + id));
    }

    @Transactional
    public Vehicle updateVehicle(Long id, @Valid VehicleDTO updatedVehicleDTO) {
        if (updatedVehicleDTO == null) {
            throw new IllegalArgumentException("Updated vehicle data cannot be null.");
        }

        Vehicle existingVehicle = getVehicleById(id);
        Vehicle updatedVehicle = Vehicle.builder()
                .brand(updatedVehicleDTO.getBrand())
                .model(updatedVehicleDTO.getModel())
                .modelYear(updatedVehicleDTO.getModelYear())
                .vehicleIdentificationNumber(updatedVehicleDTO.getVehicleIdentificationNumber())
                .price(updatedVehicleDTO.getPrice())
                .build();

        updatedVehicle.setId(existingVehicle.getId());

        return vehicleRepository.save(existingVehicle);
    }
}
