package com.tour.booking.tyme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tour.booking.tyme.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {

}
