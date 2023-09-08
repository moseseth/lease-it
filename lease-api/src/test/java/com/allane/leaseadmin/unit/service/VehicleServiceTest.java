package com.allane.leaseadmin.unit.service;

import com.allane.leaseadmin.dto.VehicleDTO;
import com.allane.leaseadmin.exception.ResourceNotFoundException;
import com.allane.leaseadmin.model.Vehicle;
import com.allane.leaseadmin.repository.VehicleRepository;
import com.allane.leaseadmin.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VehicleServiceTest {
    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateVehicle_Success() {
        Vehicle vehicle = Vehicle.builder()
                .brand("Toyota")
                .model("Camry")
                .modelYear(2022)
                .vehicleIdentificationNumber("ABC123")
                .price(BigDecimal.valueOf(25000))
                .build();

        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle createdVehicle = vehicleService.createVehicle(vehicle);

        assertNotNull(createdVehicle);
        assertEquals("Toyota", createdVehicle.getBrand());
        assertEquals("Camry", createdVehicle.getModel());
        assertEquals(2022, createdVehicle.getModelYear());
        assertEquals("ABC123", createdVehicle.getVehicleIdentificationNumber());
        assertEquals(BigDecimal.valueOf(25000), createdVehicle.getPrice());
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    public void testGetAllVehicles_Success() {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle());
        vehicles.add(new Vehicle());
        vehicles.add(new Vehicle());

        when(vehicleRepository.findAll()).thenReturn(vehicles);

        List<Vehicle> result = vehicleService.getAllVehicles();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(vehicleRepository, times(1)).findAll();
    }

    @Test
    public void testGetVehicleById_VehicleFound() {
        Long vehicleId = 1L;
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));

        Vehicle result = vehicleService.getVehicleById(vehicleId);

        assertNotNull(result);
        assertEquals(vehicleId, result.getId());
        verify(vehicleRepository, times(1)).findById(vehicleId);
    }

    @Test
    public void testGetVehicleById_VehicleNotFound() {
        Long vehicleId = 1L;

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vehicleService.getVehicleById(vehicleId));
    }

    @Test
    public void testUpdateVehicle_Success() {
        Long vehicleId = 1L;
        VehicleDTO updatedVehicleDTO = VehicleDTO.builder()
                .brand("UpdatedBrand")
                .model("UpdatedModel")
                .modelYear(2023)
                .vehicleIdentificationNumber("UpdatedVIN")
                .price(BigDecimal.valueOf(27000.0))
                .build();

        Vehicle existingVehicle = new Vehicle();
        existingVehicle.setId(vehicleId);

        Vehicle patchedVehicle = Vehicle.builder()
                .id(1L)
                .brand("UpdatedBrand")
                .model("UpdatedModel")
                .modelYear(2023)
                .vehicleIdentificationNumber("UpdatedVIN")
                .price(BigDecimal.valueOf(27000))
                .build();

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(existingVehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(patchedVehicle);

        Vehicle updatedVehicle = vehicleService.updateVehicle(vehicleId, updatedVehicleDTO);

        assertNotNull(updatedVehicle);
        assertEquals(vehicleId, updatedVehicle.getId());
        assertEquals("UpdatedBrand", updatedVehicle.getBrand());
        assertEquals("UpdatedModel", updatedVehicle.getModel());
        assertEquals(2023, updatedVehicle.getModelYear());
        assertEquals("UpdatedVIN", updatedVehicle.getVehicleIdentificationNumber());
        assertEquals(BigDecimal.valueOf(27000), updatedVehicle.getPrice());
        verify(vehicleRepository, times(1)).findById(vehicleId);
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    public void testUpdateVehicle_VehicleNotFound() {
        Long vehicleId = 1L;
        VehicleDTO updatedVehicleDTO = VehicleDTO.builder().build();

        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> vehicleService.updateVehicle(vehicleId, updatedVehicleDTO));
    }
}

