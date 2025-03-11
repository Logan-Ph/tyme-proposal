package com.tour.booking.tyme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tour.booking.tyme.entity.Tour;
import com.tour.booking.tyme.service.Tour.TourCategory;


public interface TourRepository extends JpaRepository<Tour, String> {
    @Query("select t from Tour t where " +
        "lower(t.name) like lower(concat('%', :query, '%')) or " +
        "lower(t.description) like lower(concat('%', :query, '%'))")
    List<Tour> searchTours(@Param("query") String query);

    List<Tour> findByCategory(TourCategory category);

    List<Tour> findByPriceBetween(double minPrice, double maxPrice);

    List<Tour> findByAvailabilityGreaterThanEqual(Integer availability);
}
