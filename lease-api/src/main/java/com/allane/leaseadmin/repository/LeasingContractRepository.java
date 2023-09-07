package com.allane.leaseadmin.repository;

import com.allane.leaseadmin.model.Customer;
import com.allane.leaseadmin.model.LeasingContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeasingContractRepository extends JpaRepository<LeasingContract, Long> {
    List<LeasingContract> findByCustomer(Customer customer);
}
