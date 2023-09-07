package com.allane.leaseadmin.controller;

import com.allane.leaseadmin.dto.CustomerEditRequest;
import com.allane.leaseadmin.model.Customer;
import com.allane.leaseadmin.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable Long id, @RequestBody CustomerEditRequest request) {
        boolean success = customerService.updateCustomer(id, request);

        if (success) {
            return ResponseEntity.ok("Customer updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to update customer.");
        }
    }
}
