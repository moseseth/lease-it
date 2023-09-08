package com.allane.leaseadmin.controller;

import com.allane.leaseadmin.dto.ContractOverviewDTO;
import com.allane.leaseadmin.dto.ErrorResponse;
import com.allane.leaseadmin.dto.LeasingContractDTO;
import com.allane.leaseadmin.dto.ValidationErrorResponse;
import com.allane.leaseadmin.model.LeasingContract;
import com.allane.leaseadmin.service.LeaseContractService;
import com.allane.leaseadmin.util.ValidationHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/leasing-contracts")
@Tag(name = "Lease Contracts", description = "API endpoints for managing lease contract information")
public class LeaseContractController {

    private final LeaseContractService leaseContractService;

    @Autowired
    public LeaseContractController(LeaseContractService leaseContractService) {
        this.leaseContractService = leaseContractService;
    }

    @GetMapping("/overview")
    public ResponseEntity<Page<ContractOverviewDTO>> getContractOverview(Pageable pageable) {
        Page<ContractOverviewDTO> contractOverviews = leaseContractService.getContractOverviews(pageable);
        return ResponseEntity.ok(contractOverviews);
    }

    @PostMapping
    public ResponseEntity<?> createLeasingContract(@Valid @RequestBody LeasingContractDTO leasingContractDTO,
                                                   BindingResult bindingResult) {
        List<ValidationErrorResponse> validationErrorsResponse = ValidationHelper.handleValidationErrors(bindingResult);
        if (validationErrorsResponse != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorsResponse);
        }

        LeasingContract createdContract = leaseContractService.createLeasingContract(leasingContractDTO);
        return new ResponseEntity<>(createdContract, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeasingContract> updateLeasingContract(
            @PathVariable Long id,
            @Valid @RequestBody LeasingContractDTO leasingContractDTO
    ) {
        LeasingContract updatedContract = leaseContractService.updateLeasingContract(id, leasingContractDTO);
        return ResponseEntity.ok(updatedContract);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLeasingContractDetails(@PathVariable Long id) {
        try {
            LeasingContract leasingContract = leaseContractService.getLeasingContractById(id);
            return ResponseEntity.ok(leasingContract);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(List.of(new ErrorResponse("LeasingContract not found",
                            "The specified contract ID does not exist.")));
        }
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<?> getAllLeasingByCustomer(@PathVariable Long id) {
        List<LeasingContract> leasingContract = leaseContractService.getAllLeasingContractsByCustomerId(id);
        return ResponseEntity.ok(leasingContract);
    }
}
