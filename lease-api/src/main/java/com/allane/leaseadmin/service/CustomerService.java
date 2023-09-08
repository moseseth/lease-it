package com.allane.leaseadmin.service;

import com.allane.leaseadmin.dto.CustomerEditRequest;
import com.allane.leaseadmin.dto.ErrorResponse;
import com.allane.leaseadmin.exception.ResourceNotFoundException;
import com.allane.leaseadmin.model.Customer;
import com.allane.leaseadmin.model.Vehicle;
import com.allane.leaseadmin.repository.CustomerRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    @Transactional
    public Customer updateCustomer(Long id, @Valid CustomerEditRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Updated customer data cannot be null.");
        }

        LocalDate birthdate = LocalDate.parse(request.birthdate());

        Customer existingCustomer = getCustomerById(id);
        Customer updatedCustomer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .birthdate(birthdate)
                .build();

        updatedCustomer.setId(existingCustomer.getId());

        return customerRepository.save(updatedCustomer);
    }

}
