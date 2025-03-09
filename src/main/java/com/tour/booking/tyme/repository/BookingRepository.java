package com.tour.booking.tyme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tour.booking.tyme.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, String> {

}
