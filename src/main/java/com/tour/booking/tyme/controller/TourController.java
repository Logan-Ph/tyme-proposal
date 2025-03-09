package com.tour.booking.tyme.controller;

import com.tour.booking.tyme.entity.Tour;
import com.tour.booking.tyme.exception.ErrorResponse;
import com.tour.booking.tyme.service.Tour.TourService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;

import java.util.List;

@RestController
@Validated
@RequestMapping("/tours")
@Tag(name = "Tour Controller", description = "Endpoints for managing tours")
public class TourController {
    
    @Autowired
    private TourService tourService;

    @GetMapping
    @Operation(summary = "Get all tours", description = "Retrieve a list of all tours")
    public ResponseEntity<Object> getAllTours(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size)
    {
        try {
            if (page < 0 || size <= 0) {
                return new ResponseEntity<>(new ErrorResponse("Invalid request parameters"), HttpStatus.BAD_REQUEST);
            }

            // Get all tours
            Pageable pageable = PageRequest.of(page, size);

            // Map the tours to the TourResponse object
            Page<Tour> tours = tourService.getAllTours(pageable)
            .map(tour -> Tour.builder()
                .id(tour.getId())
                .name(tour.getName())
                .description(tour.getDescription())
                .price(tour.getPrice())
                .availability(tour.getAvailability())
                .category(tour.getCategory())
                .build());
            return new ResponseEntity<>(tours, HttpStatus.OK);
        } catch (PropertyReferenceException e) {
            return new ResponseEntity<>(new ErrorResponse("Invalid request parameters"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a tour by ID", description = "Retrieve a tour by its unique identifier")
    public ResponseEntity<Object> getTourById(@PathVariable String id) {
        if (id == null || id.isBlank()) {
            return new ResponseEntity<>(new ErrorResponse("Invalid request parameters"), HttpStatus.BAD_REQUEST);
        }

        try {
            // Get the tour by ID
            Tour tour = tourService.getTourById(id);

            // Map the tour to the TourResponse object
            Tour tourResponse = Tour.builder()
                .id(tour.getId())
                .name(tour.getName())
                .description(tour.getDescription())
                .price(tour.getPrice())
                .availability(tour.getAvailability())
                .category(tour.getCategory())
                .build();
            return tourResponse != null ? 
                new ResponseEntity<>(tourResponse, HttpStatus.OK) :
                new ResponseEntity<>(new ErrorResponse("Tour not found"), HttpStatus.NOT_FOUND);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(new ErrorResponse("Tour not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @Operation(summary = "Create a new tour", description = "Create a new tour with the provided details")
    public ResponseEntity<Object> createTour(@RequestBody Tour tour) {
        if (tour == null) {
            return new ResponseEntity<>(new ErrorResponse("Invalid request body"), HttpStatus.BAD_REQUEST);
        }
        try {
            Tour createdTour = tourService.createTour(tour);
            return new ResponseEntity<>(createdTour, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ErrorResponse("Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bulk")
    @Operation(summary = "Create multiple tours", description = "Create multiple tours with the provided details")
    public ResponseEntity<Object> createTours(@RequestBody List<Tour> tours) {

        // Check if the request body is valid
        if (tours == null || tours.isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse("Invalid request body"), HttpStatus.BAD_REQUEST);
        }
        try {
            // Create the tours
            List<Tour> createdTours = tourService.createTours(tours);
            return new ResponseEntity<>(createdTours, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ErrorResponse("Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a tour", description = "Update an existing tour with the provided details")
    public ResponseEntity<Object> updateTour(@PathVariable String id, @RequestBody Tour updatedTour) {
        if (id == null || id.isBlank() || updatedTour == null) {
            return new ResponseEntity<>(new ErrorResponse("Invalid request parameters"), HttpStatus.BAD_REQUEST);
        }
        Tour tour = tourService.updateTour(id, updatedTour);
        return tour != null ? 
            new ResponseEntity<>(tour, HttpStatus.OK) :
            new ResponseEntity<>(new ErrorResponse("Tour not found"), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a tour", description = "Delete a tour by its unique identifier")
    public ResponseEntity<Object> deleteTour(@PathVariable String id) {
        if (id == null || id.isBlank()) {
            return new ResponseEntity<>(new ErrorResponse("Invalid request parameters"), HttpStatus.BAD_REQUEST);
        }
        try {
            tourService.deleteTour(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}