package com.allane.leaseadmin.unit.service;

import com.allane.leaseadmin.dto.ContractOverviewDTO;
import com.allane.leaseadmin.dto.LeasingContractDTO;
import com.allane.leaseadmin.exception.EntityNotFoundException;
import com.allane.leaseadmin.model.Customer;
import com.allane.leaseadmin.model.LeasingContract;
import com.allane.leaseadmin.model.Vehicle;
import com.allane.leaseadmin.repository.CustomerRepository;
import com.allane.leaseadmin.repository.LeasingContractRepository;
import com.allane.leaseadmin.repository.VehicleRepository;
import com.allane.leaseadmin.service.LeaseContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LeaseContractServiceTest {
    @InjectMocks
    private LeaseContractService leaseContractService;

    @Mock
    private LeasingContractRepository leasingContractRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetContractOverviews_EmptyPage() {
        Pageable pageable = Pageable.unpaged();
        Page<LeasingContract> emptyContractPage = Page.empty();
        when(leasingContractRepository.findAll(pageable)).thenReturn(emptyContractPage);

        Page<ContractOverviewDTO> contractOverviews = leaseContractService.getContractOverviews(pageable);

        assertNotNull(contractOverviews);
        assertTrue(contractOverviews.isEmpty());
    }

    @Test
    public void testCreateLeasingContract_CustomerNotFound() {
        LeasingContractDTO dto = LeasingContractDTO.builder().build();

        when(customerRepository.findById(dto.customerId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> leaseContractService.createLeasingContract(dto));
    }

    @Test
    public void testUpdateLeasingContract_ContractNotFound() {
        Long contractId = 1L;
        LeasingContractDTO dto = LeasingContractDTO.builder().build();

        when(leasingContractRepository.findById(contractId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> leaseContractService.updateLeasingContract(contractId, dto));
    }

    @Test
    public void testGetAllLeasingContractsByCustomerId_CustomerNotFound() {
        Long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> leaseContractService.getAllLeasingContractsByCustomerId(customerId));
    }

    @Test
    public void testGetLeasingContractById_ContractNotFound() {
        Long contractId = 1L;

        when(leasingContractRepository.findById(contractId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> leaseContractService.getLeasingContractById(contractId));
    }

    @Test
    public void testCreateLeasingContract_Success() {
        LeasingContractDTO dto = LeasingContractDTO.builder().build();
        Customer customer = Customer.builder().build();
        Vehicle vehicle = Vehicle.builder().build();
        LeasingContract newContract = new LeasingContract();

        when(customerRepository.findById(dto.customerId())).thenReturn(Optional.of(customer));
        when(vehicleRepository.findById(dto.vehicleId())).thenReturn(Optional.of(vehicle));
        when(leasingContractRepository.save(any(LeasingContract.class))).thenReturn(newContract);

        LeasingContract createdContract = leaseContractService.createLeasingContract(dto);

        assertNotNull(createdContract);
        assertNull(createdContract.getCustomer());
        assertNull(createdContract.getVehicle());
        verify(leasingContractRepository, times(1)).save(any(LeasingContract.class));
    }
}
