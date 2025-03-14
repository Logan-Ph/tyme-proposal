package com.tour.booking.tyme.controller;

import com.tour.booking.tyme.entity.Tour;
import com.tour.booking.tyme.service.Tour.TourCategory;
import com.tour.booking.tyme.service.Tour.TourService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tour.booking.tyme.mapper.TourMapper;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.tour.booking.tyme.dto.request.TourRequest;
import com.tour.booking.tyme.dto.response.TourResponse;

@RestController
@RequestMapping("/tours")
@Tag(name = "Tour Controller", description = "Endpoints for managing tours")
public class TourController {
    
    @Autowired
    private TourService tourService;

    @Autowired
    private TourMapper tourMapper;

    @GetMapping
    @Operation(summary = "Get all tours", description = "Retrieve a list of all tours")
    public ResponseEntity<Object> getAllTours(
        @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page must be greater than or equal to 0") int page,
        @RequestParam(defaultValue = "10") @Min(value = 1, message = "Size must be greater than 0") int size)
    {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Tour> tours = tourService.getAllTours(pageable);
            List<TourResponse> toursResponse = tourMapper.toDTOs(tours.getContent());
            return new ResponseEntity<>(toursResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a tour by ID", description = "Retrieve a tour by its unique identifier")
    public ResponseEntity<Object> getTourById(
        @PathVariable @NotBlank @Size(min = 24, max = 128) String id) {
        try {
            TourResponse tourResponse = tourMapper.toDTO(tourService.getTourById(id));
            return tourResponse != null ?
                new ResponseEntity<>(tourResponse, HttpStatus.OK) :
                new ResponseEntity<>("Tour not found", HttpStatus.NOT_FOUND);
        } catch (NullPointerException e) {
            return new ResponseEntity<>("Tour not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @Operation(summary = "Create a new tour", description = "Create a new tour with the provided details")
    public ResponseEntity<Object> createTour(@RequestBody @NotNull TourRequest tourRequest) {
        try {
            Tour createdTour = tourService.createTour(tourMapper.toEntity(tourRequest));
            TourResponse createdTourResponse = tourMapper.toDTO(createdTour);
            return new ResponseEntity<>(createdTourResponse, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create multiple tours", description = "Create multiple tours with the provided details")
    public ResponseEntity<Object> createTours(@RequestBody @NotNull List<TourRequest> tourRequests) {
        try {
            List<Tour> createdTours = tourService.createTours(tourMapper.toEntities(tourRequests));
            List<TourResponse> createdTourResponses = tourMapper.toDTOs(createdTours);
            return new ResponseEntity<>(createdTourResponses, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a tour", description = "Update an existing tour with the provided details")
    public ResponseEntity<Object> updateTour(
        @PathVariable @NotBlank @Size(min = 24, max = 128) String id,
        @RequestBody @NotNull TourRequest tourRequest) {
        try {
            Tour tour = tourService.getTourById(id);
            if (tour == null) {
                return new ResponseEntity<>("Tour not found", HttpStatus.NOT_FOUND);
            }
            tour = tourMapper.toEntity(tourRequest);
            tour.setId(id);
            tour = tourService.updateTour(tour);
            TourResponse updatedTourResponse = tourMapper.toDTO(tour);

            return updatedTourResponse != null ? 
                new ResponseEntity<>(updatedTourResponse, HttpStatus.OK) :
                new ResponseEntity<>("Tour not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a tour", description = "Delete a tour by its unique identifier")
    public ResponseEntity<Object> deleteTour(@PathVariable 
        @NotBlank @Size(min = 24, max = 128) String id) {
        try {
            Tour tour = tourService.getTourById(id);
            if (tour == null) {
                return new ResponseEntity<>("Tour not found", HttpStatus.NOT_FOUND);
            }
            tourService.deleteTour(id);
            return new ResponseEntity<>("Tour deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search for tours", description = "Search for tours by name or description")
    public ResponseEntity<Object> searchTours(
        @RequestParam @NotBlank String query) {
        try {
            List<TourResponse> toursResponse = tourMapper.toDTOs(tourService.searchTours(query));
            return new ResponseEntity<>(toursResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/filter/category")
    @Operation(summary = "Filter tours by category", description = "Filter tours by category")
    public ResponseEntity<Object> filterToursByCategory(
        @RequestParam @NotNull TourCategory category) {
        try {
            List<TourResponse> toursResponse = tourMapper.toDTOs(tourService.filterToursByCategory(category));
            return new ResponseEntity<>(toursResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/filter/price")
    @Operation(summary = "Filter tours by price", description = "Filter tours by price")
    public ResponseEntity<Object> filterToursByPrice(
            @RequestParam @Min(value = 0, message = "Minimum price must be greater than or equal to 0") double minPrice,
            @RequestParam @Min(value = 0, message = "Maximum price must be greater than or equal to 0") double maxPrice) {
        if (minPrice > maxPrice) 
            return new ResponseEntity<>("Minimum price cannot be greater than maximum price", HttpStatus.BAD_REQUEST);
    
        try {
            List<TourResponse> toursResponse = tourMapper.toDTOs(tourService.filterToursByPrice(minPrice, maxPrice));
            return new ResponseEntity<>(toursResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/filter/availability")
    @Operation(summary = "Filter tours by availability", description = "Filter tours by availability")
    public ResponseEntity<Object> filterToursByAvailability(
            @RequestParam @Min(value = 0, message = "Minimum availability must be greater than or equal to 0") int minAvailability) {
        try {
            List<TourResponse> toursResponse = tourMapper.toDTOs(tourService.filterToursByAvailability(minAvailability));
            return new ResponseEntity<>(toursResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}