package com.tour.booking.tyme.service.Tour;


import com.tour.booking.tyme.entity.Tour;
import com.tour.booking.tyme.repository.TourRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TourService {
    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public Page<Tour> getAllTours(Pageable pageable) {
        return tourRepository.findAll(pageable);
    }

    public Tour getTourById(String id) {
        return tourRepository.findById(id).orElse(null);
    }

    public Tour createTour(Tour tour) {
        return tourRepository.save(tour);
    }

    public List<Tour> createTours(List<Tour> tours) {
        return tourRepository.saveAll(tours);
    }

    public Tour updateTour(String id, Tour updatedTour) {
        Tour tour = tourRepository.findById(id).orElse(null);
        if (tour != null) {
            // Update tour fields
            tour.setName(updatedTour.getName());
            tour.setDescription(updatedTour.getDescription());
            tour.setPrice(updatedTour.getPrice());
            tour.setAvailability(updatedTour.getAvailability());
            tour.setCategory(updatedTour.getCategory());
            return tourRepository.save(tour);
        }
        return null;
    }

    public void deleteTour(String id) {
        tourRepository.deleteById(id);
    }
}