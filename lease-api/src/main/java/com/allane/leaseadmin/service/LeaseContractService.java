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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
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

    public Page<ContractOverviewDTO> getContractOverviews(Pageable pageable) {
        Page<LeasingContract> contractPage = leasingContractRepository.findAll(pageable);

        return contractPage.map(this::mapToContractOverviewDTO);
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
                .contractDetailsLink("/api/leasing-contracts/" + contract.getId())
                .build();
    }

    @Transactional
    public LeasingContract createLeasingContract(@Valid LeasingContractDTO leasingContractDTO) {
        LeasingContract newContract = createOrUpdateLeasingContract(new LeasingContract(), leasingContractDTO);
        return leasingContractRepository.save(newContract);
    }

    @Transactional
    public LeasingContract updateLeasingContract(Long id, @Valid LeasingContractDTO leasingContractDTO) {
        LeasingContract existingContract = leasingContractRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LeasingContract not found with ID: " + id));
        LeasingContract updatedContract =  createOrUpdateLeasingContract(existingContract, leasingContractDTO);

        return leasingContractRepository.save(updatedContract);
    }

    private LeasingContract createOrUpdateLeasingContract(LeasingContract contract, LeasingContractDTO dto) {
        contract.setMonthlyRate(dto.monthlyRate());

        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + dto.customerId()));

        Vehicle vehicle = vehicleRepository.findById(dto.vehicleId())
                .orElseThrow(() -> new EntityNotFoundException("Vehicle not found with ID: " + dto.vehicleId()));

        contract.setCustomer(customer);
        contract.setVehicle(vehicle);

        return contract;
    }

    @Transactional(readOnly = true)
    public List<LeasingContract> getAllLeasingContractsByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with ID: " + customerId));

        return leasingContractRepository.findByCustomer(customer);
    }

    @Transactional(readOnly = true)
    public LeasingContract getLeasingContractById(Long id) {
        Optional<LeasingContract> optionalContract = leasingContractRepository.findById(id);

        if (optionalContract.isPresent()) {
            return optionalContract.get();
        } else {
            throw new NoSuchElementException("LeasingContract not found");
        }
    }
}

