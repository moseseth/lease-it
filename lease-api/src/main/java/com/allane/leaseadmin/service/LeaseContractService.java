package com.allane.leaseadmin.service;

import com.allane.leaseadmin.dto.ContractOverviewDTO;
import com.allane.leaseadmin.dto.LeasingContractDTO;
import com.allane.leaseadmin.exception.EntityNotFoundException;
import com.allane.leaseadmin.model.Customer;
import com.allane.leaseadmin.model.LeasingContract;
import com.allane.leaseadmin.model.Vehicle;
import com.allane.leaseadmin.repository.CustomerRepository;
import com.allane.leaseadmin.repository.LeasingContractRepository;
import com.allane.leaseadmin.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaseContractService {

    private final LeasingContractRepository leasingContractRepository;
    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    @Autowired
    public LeaseContractService(
            LeasingContractRepository leasingContractRepository,
            CustomerRepository customerRepository,
            VehicleRepository vehicleRepository
    ) {
        this.leasingContractRepository = leasingContractRepository;
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<ContractOverviewDTO> getContractOverviews() {
        List<LeasingContract> contracts = leasingContractRepository.findAll();
        return contracts.stream().map(this::mapToContractOverviewDTO).collect(Collectors.toList());
    }

    private ContractOverviewDTO mapToContractOverviewDTO(LeasingContract contract) {
        return ContractOverviewDTO.builder()
                .contractNumber(contract.getContractNumber())
                .customerSummary(contract.getCustomer().getFirstName() + " " + contract.getCustomer().getLastName())
                .vehicleBrand(contract.getVehicle().getBrand())
                .vehicleModel(contract.getVehicle().getModel())
                .vehicleModelYear(contract.getVehicle().getModelYear())
                .vehicleVin(contract.getVehicle().getVehicleIdentificationNumber())
                .monthlyRate(contract.getMonthlyRate())
                .vehiclePrice(contract.getVehicle().getPrice())
                .contractDetailsLink("/leasing-contracts/" + contract.getId())
                .build();
    }

    @Transactional
    public LeasingContract createLeasingContract(LeasingContractDTO leasingContractDTO) {
        LeasingContract newContract = createOrUpdateLeasingContract(new LeasingContract(), leasingContractDTO);
        return leasingContractRepository.save(newContract);
    }

    @Transactional
    public LeasingContract updateLeasingContract(Long id, LeasingContractDTO leasingContractDTO) {
        LeasingContract existingContract = leasingContractRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LeasingContract not found with ID: " + id));

        return createOrUpdateLeasingContract(existingContract, leasingContractDTO);
    }

    private LeasingContract createOrUpdateLeasingContract(LeasingContract contract, LeasingContractDTO dto) {
        contract.setContractNumber(dto.getContractNumber());
        contract.setMonthlyRate(dto.getMonthlyRate());

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + dto.getCustomerId()));

        Vehicle vehicle = vehicleRepository.findById(dto.getVehicleId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with ID: " + dto.getVehicleId()));

        contract.setCustomer(customer);
        contract.setVehicle(vehicle);

        return contract;
    }

    public List<LeasingContract> getAllLeasingContractsByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));

        return leasingContractRepository.findByCustomer(customer);
    }
}

