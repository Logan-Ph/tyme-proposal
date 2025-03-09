package com.tour.booking.tyme.TourControllerTest;

import com.tour.booking.tyme.controller.TourController;
import com.tour.booking.tyme.entity.Tour;
import com.tour.booking.tyme.exception.ErrorResponse;
import com.tour.booking.tyme.service.Tour.TourService;
import com.tour.booking.tyme.service.Tour.TourCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TourControllerTest {

    @Mock
    private TourService tourService;

    @InjectMocks
    private TourController tourController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTours_ValidRequest_ReturnsOk() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Tour tour = createSampleTour();
        Page<Tour> page = new PageImpl<>(Collections.singletonList(tour));
        when(tourService.getAllTours(any(Pageable.class))).thenReturn(page);

        // Act
        ResponseEntity<Object> response = tourController.getAllTours(0, 10);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(tourService, times(1)).getAllTours(pageable);
    }

    @Test
    void getAllTours_InvalidPage_ReturnsBadRequest() {
        // Act
        ResponseEntity<Object> response = tourController.getAllTours(-1, 10);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }

    @Test
    void getAllTours_InvalidSize_ReturnsBadRequest() {
        // Act
        ResponseEntity<Object> response = tourController.getAllTours(0, 0);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }

    @Test
    void getTourById_ValidId_ReturnsOk() {
        // Arrange
        String id = UUID.randomUUID().toString();
        Tour tour = createSampleTour();
        when(tourService.getTourById(id)).thenReturn(tour);

        // Act
        ResponseEntity<Object> response = tourController.getTourById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(tourService, times(1)).getTourById(id);
    }

    @Test
    void getTourById_InvalidId_ReturnsBadRequest() {
        // Act
        ResponseEntity<Object> response = tourController.getTourById("");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }

    @Test
    void getTourById_NotFound_ReturnsNotFound() {
        // Arrange
        String id = UUID.randomUUID().toString();
        when(tourService.getTourById(id)).thenReturn(null);

        // Act
        ResponseEntity<Object> response = tourController.getTourById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }

    @Test
    void createTour_ValidTour_ReturnsCreated() {
        // Arrange
        Tour tour = createSampleTour();
        when(tourService.createTour(any(Tour.class))).thenReturn(tour);

        // Act
        ResponseEntity<Object> response = tourController.createTour(tour);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(tourService, times(1)).createTour(tour);
    }

    @Test
    void createTour_NullTour_ReturnsBadRequest() {
        // Act
        ResponseEntity<Object> response = tourController.createTour(null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }

    @Test
    void createTours_ValidList_ReturnsCreated() {
        // Arrange
        List<Tour> tours = Collections.singletonList(createSampleTour());
        when(tourService.createTours(anyList())).thenReturn(tours);

        // Act
        ResponseEntity<Object> response = tourController.createTours(tours);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(tourService, times(1)).createTours(tours);
    }

    @Test
    void createTours_EmptyList_ReturnsBadRequest() {
        // Act
        ResponseEntity<Object> response = tourController.createTours(Collections.emptyList());

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }

    @Test
    void updateTour_ValidRequest_ReturnsOk() {
        // Arrange
        String id = UUID.randomUUID().toString();
        Tour updatedTour = createSampleTour();
        when(tourService.updateTour(anyString(), any(Tour.class))).thenReturn(updatedTour);

        // Act
        ResponseEntity<Object> response = tourController.updateTour(id, updatedTour);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(tourService, times(1)).updateTour(id, updatedTour);
    }

    @Test
    void updateTour_InvalidId_ReturnsBadRequest() {
        // Act
        ResponseEntity<Object> response = tourController.updateTour("", createSampleTour());

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }

    @Test
    void updateTour_NotFound_ReturnsNotFound() {
        // Arrange
        String id = UUID.randomUUID().toString();
        when(tourService.updateTour(anyString(), any(Tour.class))).thenReturn(null);

        // Act
        ResponseEntity<Object> response = tourController.updateTour(id, createSampleTour());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }

    @Test
    void deleteTour_ValidId_ReturnsNoContent() {
        // Arrange
        String id = UUID.randomUUID().toString();
        doNothing().when(tourService).deleteTour(id);

        // Act
        ResponseEntity<Object> response = tourController.deleteTour(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tourService, times(1)).deleteTour(id);
    }

    @Test
    void deleteTour_InvalidId_ReturnsBadRequest() {
        // Act
        ResponseEntity<Object> response = tourController.deleteTour("");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }

    private Tour createSampleTour() {
        return Tour.builder()
                .id(UUID.randomUUID().toString())
                .name("Sample Tour")
                .description("Sample Description")
                .price(new BigDecimal("100.00"))
                .availability(10)
                .category(TourCategory.ADVENTURE)
                .build();
    }
}