package com.tour.booking.tyme.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tour.booking.tyme.entity.Tour;

public interface TourRepository extends JpaRepository<Tour, String> {

}
