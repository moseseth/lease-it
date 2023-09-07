package com.allane.leaseadmin.service;

import com.allane.leaseadmin.dto.CustomerEditRequest;
import com.allane.leaseadmin.exception.ResourceNotFoundException;
import com.allane.leaseadmin.model.Customer;
import com.allane.leaseadmin.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    public boolean updateCustomer(Long id, CustomerEditRequest request) {
        if (!isValidBirthdateFormat(request.getBirthdate())) {
            return false;
        }

        LocalDate birthdate = LocalDate.parse(request.getBirthdate());

        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            Customer existingCustomer = customer.get();
            existingCustomer.setFirstName(request.getFirstName());
            existingCustomer.setLastName(request.getLastName());
            existingCustomer.setBirthdate(birthdate);

            existingCustomer.setId(existingCustomer.getId());

            customerRepository.save(existingCustomer);

            return true;
        }

        return false;
    }

    public boolean isValidBirthdateFormat(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
