package com.allane.leaseadmin.service;

import com.allane.leaseadmin.dto.VehicleDTO;
import com.allane.leaseadmin.exception.ResourceNotFoundException;
import com.allane.leaseadmin.model.Customer;
import com.allane.leaseadmin.model.Vehicle;
import com.allane.leaseadmin.repository.VehicleRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public Vehicle createVehicle(@Valid Vehicle vehicle) {
        Vehicle newVehicle = Vehicle.builder()
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .modelYear(vehicle.getModelYear())
                .vehicleIdentificationNumber(vehicle.getVehicleIdentificationNumber())
                .price(vehicle.getPrice())
                .build();

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
                .brand(updatedVehicleDTO.brand())
                .model(updatedVehicleDTO.model())
                .modelYear(updatedVehicleDTO.modelYear())
                .vehicleIdentificationNumber(updatedVehicleDTO.vehicleIdentificationNumber())
                .price(updatedVehicleDTO.price())
                .build();

        updatedVehicle.setId(existingVehicle.getId());

        return vehicleRepository.save(updatedVehicle);
    }
}
