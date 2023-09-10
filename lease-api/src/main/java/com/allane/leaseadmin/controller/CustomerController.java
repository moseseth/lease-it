package com.allane.leaseadmin.controller;

import com.allane.leaseadmin.dto.CustomerEditRequest;
import com.allane.leaseadmin.dto.ErrorResponse;
import com.allane.leaseadmin.dto.ValidationErrorResponse;
import com.allane.leaseadmin.model.Customer;
import com.allane.leaseadmin.service.CustomerService;
import com.allane.leaseadmin.util.ValidationHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.allane.leaseadmin.util.ValidationHelper.isValidBirthdateFormat;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer", description = "API endpoints for managing customer information")
public class CustomerController {
    private final CustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult) {
        List<ValidationErrorResponse> validationErrorsResponse = ValidationHelper.handleValidationErrors(bindingResult);
        if (validationErrorsResponse != null) {
            logger.warn("Validation errors occurred while creating a customer: {}", validationErrorsResponse);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorsResponse);
        }

        Customer createdCustomer = customerService.createCustomer(customer);
        logger.info("Created a new customer with ID: {}", createdCustomer.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        logger.info("Retrieved customer with ID: {}", id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerEditRequest request) {
        if (!isValidBirthdateFormat(request.birthdate())) {
            logger.warn("Invalid birthdate format received in update request for customer with ID: {}", id);
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse("VALIDATION_INVALID_BIRTHDATE", "Invalid birthdate"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        Customer updatedCustomer = customerService.updateCustomer(id, request);
        logger.info("Updated customer with ID: {}", id);
        return ResponseEntity.ok(updatedCustomer);
    }
}
