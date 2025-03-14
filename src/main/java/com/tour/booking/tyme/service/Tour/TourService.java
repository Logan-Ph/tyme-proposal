package com.tour.booking.tyme.service.Tour;


import com.tour.booking.tyme.entity.Tour;
import com.tour.booking.tyme.repository.TourRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
public class TourService {
    @Autowired
    private TourRepository tourRepository;

    public Page<Tour> getAllTours(Pageable pageable) {
        return tourRepository.findAll(pageable);
    }

    public Tour getTourById(String id) {
        return tourRepository.findById(id)
            .orElse(null);
    }

    @Transactional
    public Tour createTour(Tour tour) {
        Tour createdTour = tourRepository.save(tour);
        return createdTour;
    }

    @Transactional
    public List<Tour> createTours(List<Tour> tours) {
        List<Tour> createdTours = tourRepository.saveAll(tours);
        return createdTours;
    }

    @Transactional
    public Tour updateTour(Tour tour) {
        return tourRepository.save(tour);
    }

    @Transactional
    public void deleteTour(String id) {
        tourRepository.deleteById(id);
    }

    public List<Tour> searchTours(String query) {
        String sanitizedQuery = sanitizeSearchQuery(query);
        return tourRepository.searchTours(sanitizedQuery);
    }

    public List<Tour> filterToursByCategory(TourCategory category) {
        return tourRepository.findByCategory(category);
    }

    public List<Tour> filterToursByPrice(double minPrice, double maxPrice) {
        return tourRepository.findByPriceBetween(minPrice, maxPrice);
    }

    public List<Tour> filterToursByAvailability(int minAvailability) {
        return tourRepository.findByAvailabilityGreaterThanEqual(minAvailability);
    }

    private String sanitizeSearchQuery(String query) {
        if (query == null) {
            return "";
        }
        // Remove special characters that could be used for SQL injection
        return query.replaceAll("[^a-zA-Z0-9\\s]", "");
    }
}