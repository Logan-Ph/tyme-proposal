package com.tour.booking.tyme.TourControllerTest;

import com.tour.booking.tyme.controller.TourController;
import com.tour.booking.tyme.dto.request.TourRequest;
import com.tour.booking.tyme.dto.response.TourResponse;
import com.tour.booking.tyme.entity.Tour;
import com.tour.booking.tyme.service.Tour.TourCategory;
import com.tour.booking.tyme.service.Tour.TourService;
import com.tour.booking.tyme.mapper.TourMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TourControllerTest {

    @Mock
    private TourService tourService;

    @Mock
    private TourMapper tourMapper;

    @InjectMocks
    private TourController tourController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTours_ValidRequest_ReturnsOk() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<Tour> tours = new ArrayList<>();
        tours.add(new Tour());
        Page<Tour> tourPage = new PageImpl<>(tours);
        List<TourResponse> tourResponses = Collections.singletonList(mock(TourResponse.class));
        when(tourService.getAllTours(pageable)).thenReturn(tourPage);
        when(tourMapper.toDTOs(tours)).thenReturn(tourResponses);
        ResponseEntity<Object> response = tourController.getAllTours(page, size);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tourResponses, response.getBody());
        verify(tourService, times(1)).getAllTours(pageable);
    }

    @Test
    void getAllTours_InvalidParameters_ReturnsBadRequest() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        when(tourService.getAllTours(pageable)).thenThrow(new RuntimeException("Invalid request parameters"));
        ResponseEntity<Object> response = tourController.getAllTours(page, size);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getTourById_ValidId_ReturnsOk() {
        String id = UUID.randomUUID().toString();
        Tour tour = new Tour();
        TourResponse tourResponse = mock(TourResponse.class);
        when(tourService.getTourById(id)).thenReturn(tour);
        when(tourMapper.toDTO(tour)).thenReturn(tourResponse);
        ResponseEntity<Object> response = tourController.getTourById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tourResponse, response.getBody());
        verify(tourService, times(1)).getTourById(id);
    }

    @Test
    void getTourById_NotFound_ReturnsNotFound() {
        String id = UUID.randomUUID().toString();
        when(tourService.getTourById(id)).thenReturn(null);
        ResponseEntity<Object> response = tourController.getTourById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Tour not found", response.getBody());
        verify(tourService, times(1)).getTourById(id);
    }

    @Test
    void createTour_Valid_ReturnsCreated() {
        TourRequest tourRequest = new TourRequest();
        Tour tour = new Tour();
        TourResponse tourResponse = mock(TourResponse.class);
        when(tourMapper.toEntity(tourRequest)).thenReturn(tour);
        when(tourService.createTour(tour)).thenReturn(tour);
        when(tourMapper.toDTO(tour)).thenReturn(tourResponse);
        ResponseEntity<Object> response = tourController.createTour(tourRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(tourResponse, response.getBody());
        verify(tourService, times(1)).createTour(tour);
    }

    @Test
    void createTour_Invalid_ReturnsInternalServerError() {
        TourRequest tourRequest = new TourRequest();
        when(tourMapper.toEntity(tourRequest)).thenReturn(new Tour());
        when(tourService.createTour(any(Tour.class))).thenThrow(new DataIntegrityViolationException("Error"));
        ResponseEntity<Object> response = tourController.createTour(tourRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void createTours_Valid_ReturnsCreated() {
        List<TourRequest> tourRequests = new ArrayList<>();
        tourRequests.add(new TourRequest());
        List<Tour> tours = new ArrayList<>();
        tours.add(new Tour());
        List<TourResponse> tourResponses = Collections.singletonList(mock(TourResponse.class));
        when(tourMapper.toEntities(tourRequests)).thenReturn(tours);
        when(tourService.createTours(tours)).thenReturn(tours);
        when(tourMapper.toDTOs(tours)).thenReturn(tourResponses);
        ResponseEntity<Object> response = tourController.createTours(tourRequests);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(tourResponses, response.getBody());
    }

    @Test
    void updateTour_Valid_ReturnsOk() {
        String id = UUID.randomUUID().toString();
        TourRequest tourRequest = new TourRequest();
        Tour existingTour = new Tour();
        Tour updatedTour = new Tour();
        TourResponse tourResponse = mock(TourResponse.class);
        when(tourService.getTourById(id)).thenReturn(existingTour);
        when(tourMapper.toEntity(tourRequest)).thenReturn(updatedTour);
        updatedTour.setId(id);
        when(tourService.updateTour(updatedTour)).thenReturn(updatedTour);
        when(tourMapper.toDTO(updatedTour)).thenReturn(tourResponse);
        ResponseEntity<Object> response = tourController.updateTour(id, tourRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tourResponse, response.getBody());
    }

    @Test
    void updateTour_NotFound_ReturnsNotFound() {
        String id = UUID.randomUUID().toString();
        TourRequest tourRequest = new TourRequest();
        when(tourService.getTourById(id)).thenReturn(null);
        ResponseEntity<Object> response = tourController.updateTour(id, tourRequest);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteTour_Valid_ReturnsOk() {
        String id = UUID.randomUUID().toString();
        Tour tour = new Tour();
        when(tourService.getTourById(id)).thenReturn(tour);
        doNothing().when(tourService).deleteTour(id);
        ResponseEntity<Object> response = tourController.deleteTour(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(tourService, times(1)).deleteTour(id);
    }

    @Test
    void deleteTour_NotFound_ReturnsNotFound() {
        String id = UUID.randomUUID().toString();
        when(tourService.getTourById(id)).thenReturn(null);
        ResponseEntity<Object> response = tourController.deleteTour(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void searchTours_Valid_ReturnsOk() {
        String query = "beach";
        List<TourResponse> tourResponses = Collections.singletonList(mock(TourResponse.class));
        when(tourMapper.toDTOs(any())).thenReturn(tourResponses);
        when(tourService.searchTours(query)).thenReturn(new ArrayList<>());
        ResponseEntity<Object> response = tourController.searchTours(query);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tourResponses, response.getBody());
    }

    @Test
    void filterToursByCategory_Valid_ReturnsOk() {
        TourCategory category = TourCategory.ADVENTURE;
        List<TourResponse> tourResponses = Collections.singletonList(mock(TourResponse.class));
        when(tourMapper.toDTOs(any())).thenReturn(tourResponses);
        when(tourService.filterToursByCategory(category)).thenReturn(new ArrayList<>());
        ResponseEntity<Object> response = tourController.filterToursByCategory(category);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tourResponses, response.getBody());
    }

    @Test
    void filterToursByPrice_MinGreaterThanMax_ReturnsBadRequest() {
        ResponseEntity<Object> response = tourController.filterToursByPrice(200.0, 100.0);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Minimum price cannot be greater than maximum price", response.getBody());
    }

    @Test
    void filterToursByPrice_Valid_ReturnsOk() {
        double minPrice = 100.0;
        double maxPrice = 200.0;
        List<TourResponse> tourResponses = Collections.singletonList(mock(TourResponse.class));
        when(tourMapper.toDTOs(any())).thenReturn(tourResponses);
        when(tourService.filterToursByPrice(minPrice, maxPrice)).thenReturn(new ArrayList<>());
        ResponseEntity<Object> response = tourController.filterToursByPrice(minPrice, maxPrice);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tourResponses, response.getBody());
    }

    @Test
    void filterToursByAvailability_Valid_ReturnsOk() {
        int minAvailability = 5;
        List<TourResponse> tourResponses = Collections.singletonList(mock(TourResponse.class));
        when(tourMapper.toDTOs(any())).thenReturn(tourResponses);
        when(tourService.filterToursByAvailability(minAvailability)).thenReturn(new ArrayList<>());
        ResponseEntity<Object> response = tourController.filterToursByAvailability(minAvailability);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tourResponses, response.getBody());
    }
} 