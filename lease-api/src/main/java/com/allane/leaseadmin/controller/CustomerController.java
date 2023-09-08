package com.allane.leaseadmin.controller;

import com.allane.leaseadmin.dto.CustomerEditRequest;
import com.allane.leaseadmin.dto.ErrorResponse;
import com.allane.leaseadmin.dto.ValidationErrorResponse;
import com.allane.leaseadmin.model.Customer;
import com.allane.leaseadmin.service.CustomerService;
import com.allane.leaseadmin.util.ValidationHelper;
import jakarta.validation.Valid;
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
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer, BindingResult bindingResult) {
        List<ValidationErrorResponse> validationErrorsResponse = ValidationHelper.handleValidationErrors(bindingResult);
        if (validationErrorsResponse != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorsResponse);
        }

        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerEditRequest request) {
        if (!isValidBirthdateFormat(request.birthdate())) {
            List<ErrorResponse> errors = new ArrayList<>();
            errors.add(new ErrorResponse("VALIDATION_INVALID_BIRTHDATE", "Invalid birthdate"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        Customer updatedCustomer = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(updatedCustomer);
    }
}
