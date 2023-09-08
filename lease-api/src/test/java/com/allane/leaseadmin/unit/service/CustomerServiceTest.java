package com.allane.leaseadmin.unit.service;

import com.allane.leaseadmin.dto.CustomerEditRequest;
import com.allane.leaseadmin.model.Customer;
import com.allane.leaseadmin.repository.CustomerRepository;
import com.allane.leaseadmin.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceTest {
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(customer);

        assertNotNull(createdCustomer);
        assertEquals("John", createdCustomer.getFirstName());
        assertEquals("Doe", createdCustomer.getLastName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testGetCustomerById() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer retrievedCustomer = customerService.getCustomerById(1L);

        assertNotNull(retrievedCustomer);
        assertEquals("John", retrievedCustomer.getFirstName());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateCustomer() {
        CustomerEditRequest request = CustomerEditRequest.builder()
                .firstName("UpdatedJohn")
                .lastName("UpdatedDoe")
                .birthdate("1990-01-01")
                .build();

        Customer existingCustomer = new Customer();
        existingCustomer.setId(1L);
        existingCustomer.setFirstName("John");
        existingCustomer.setLastName("Doe");
        existingCustomer.setBirthdate(LocalDate.of(1990, 1, 1));

        Customer renewedCustomer = Customer.builder()
                .firstName("UpdatedJohn")
                .lastName("UpdatedDoe")
                .birthdate(LocalDate.parse("1990-01-01"))
                .build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class))).thenReturn(renewedCustomer);

        Customer updatedCustomer = customerService.updateCustomer(1L, request);

        assertNotNull(updatedCustomer);
        assertEquals("UpdatedJohn", updatedCustomer.getFirstName());
        assertEquals("UpdatedDoe", updatedCustomer.getLastName());
        assertEquals(LocalDate.parse("1990-01-01"), updatedCustomer.getBirthdate());
        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }
}

