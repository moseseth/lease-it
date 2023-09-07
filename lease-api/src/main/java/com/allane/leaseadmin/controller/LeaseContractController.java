package com.allane.leaseadmin.controller;


import com.allane.leaseadmin.dto.ContractOverviewDTO;
import com.allane.leaseadmin.dto.LeasingContractDTO;
import com.allane.leaseadmin.model.LeasingContract;
import com.allane.leaseadmin.service.LeaseContractService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leasing-contracts")
public class LeaseContractController {

    private final LeaseContractService leaseContractService;

    @Autowired
    public LeaseContractController(LeaseContractService leaseContractService) {
        this.leaseContractService = leaseContractService;
    }

    @GetMapping("/overview")
    public List<ContractOverviewDTO> getContractOverview() {
        return leaseContractService.getContractOverviews();
    }

    @PostMapping
    public ResponseEntity<LeasingContract> createLeasingContract(@Valid @RequestBody LeasingContractDTO leasingContractDTO) {
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
}
