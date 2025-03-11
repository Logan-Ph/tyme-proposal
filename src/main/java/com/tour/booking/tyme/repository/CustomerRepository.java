package com.tour.booking.tyme.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tour.booking.tyme.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String>{
    boolean existsByEmail(String email);
    Optional<Customer> findByEmail(String email);
}
