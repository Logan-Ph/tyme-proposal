package com.tour.booking.tyme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tour.booking.tyme.entity.Booking;

import java.util.Optional;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByCustomerId(String customerId);
    Optional<Booking> findByCustomerIdAndTourId(String customerId, String tourId);
}
