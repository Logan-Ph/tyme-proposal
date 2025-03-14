package com.tour.booking.tyme.BookingControllerTest;

import com.tour.booking.tyme.controller.BookingController;
import com.tour.booking.tyme.entity.Booking;
import com.tour.booking.tyme.service.Booking.BookingService;
import com.tour.booking.tyme.service.Customer.CustomerService;
import com.tour.booking.tyme.service.Tour.TourService;
import com.tour.booking.tyme.service.Discount.VoucherService;
import com.tour.booking.tyme.service.Payment.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private TourService tourService;

    @Mock
    private CustomerService customerService;

    @Mock  
    private VoucherService voucherService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBookings_ValidRequest_ReturnsOk() {
        // Arrange
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        when(bookingService.getAllBookings()).thenReturn(bookings);

        // Act
        ResponseEntity<Object> response = bookingController.getAllBookings();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookings, response.getBody());
        verify(bookingService, times(1)).getAllBookings();
    }

    @Test
    void getAllBookings_ServiceException_ReturnsInternalServerError() {
        // Arrange
        when(bookingService.getAllBookings()).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<Object> response = bookingController.getAllBookings();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getBookingById_ValidId_ReturnsOk() {
        // Arrange
        String id = UUID.randomUUID().toString();
        Booking booking = new Booking();
        when(bookingService.getBookingById(id)).thenReturn(booking);

        // Act
        ResponseEntity<Object> response = bookingController.getBookingById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(booking, response.getBody());
        verify(bookingService, times(1)).getBookingById(id);
    }

    @Test
    void getBookingById_InvalidId_ReturnsNotFound() {
        // Arrange
        String id = UUID.randomUUID().toString();
        when(bookingService.getBookingById(id)).thenReturn(null);

        // Act
        ResponseEntity<Object> response = bookingController.getBookingById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Booking not found", response.getBody());
        verify(bookingService, times(1)).getBookingById(id);
    }

    @Test
    void createBooking_ValidInputWithVoucher_ReturnsCreated() {
        // Arrange
        String customerId = UUID.randomUUID().toString();
        String tourId = UUID.randomUUID().toString();
        String voucherCode = "SUMMER_SALE";
        Booking booking = new Booking();
        when(bookingService.createBooking(customerId, tourId, voucherCode)).thenReturn(booking);

        // Act
        ResponseEntity<Object> response = bookingController.createBooking(customerId, tourId, voucherCode);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(booking, response.getBody());
        verify(bookingService, times(1)).createBooking(customerId, tourId, voucherCode);
    }

    @Test
    void createBooking_ValidInputWithoutVoucher_ReturnsCreated() {
        // Arrange
        String customerId = UUID.randomUUID().toString();
        String tourId = UUID.randomUUID().toString();
        Booking booking = new Booking();
        when(bookingService.createBooking(customerId, tourId)).thenReturn(booking);

        // Act
        ResponseEntity<Object> response = bookingController.createBooking(customerId, tourId, null);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(booking, response.getBody());
        verify(bookingService, times(1)).createBooking(customerId, tourId);
    }

    @Test
    void createBooking_InvalidInput_ReturnsBadRequest() {
        // Arrange
        String customerId = UUID.randomUUID().toString();
        String tourId = UUID.randomUUID().toString();
        String voucherCode = "INVALID_CODE";
        when(bookingService.createBooking(customerId, tourId, voucherCode)).thenThrow(new IllegalArgumentException("Invalid voucher code"));

        // Act
        ResponseEntity<Object> response = bookingController.createBooking(customerId, tourId, voucherCode);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid voucher code", response.getBody());
        verify(bookingService, times(1)).createBooking(customerId, tourId, voucherCode);
    }

    @Test
    void getBookingByCustomerIdAndTourId_ValidInput_ReturnsOk() {
        // Arrange
        String customerId = UUID.randomUUID().toString();
        String tourId = UUID.randomUUID().toString();
        Booking booking = new Booking();
        when(bookingService.getBookingByCustomerIdAndTourId(customerId, tourId)).thenReturn(booking);

        // Act
        ResponseEntity<Object> response = bookingController.getBookingByCustomerIdAndTourId(customerId, tourId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(booking, response.getBody());
        verify(bookingService, times(1)).getBookingByCustomerIdAndTourId(customerId, tourId);
    }

    @Test
    void getBookingByCustomerIdAndTourId_InvalidInput_ReturnsNotFound() {
        // Arrange
        String customerId = UUID.randomUUID().toString();
        String tourId = UUID.randomUUID().toString();
        when(bookingService.getBookingByCustomerIdAndTourId(customerId, tourId)).thenThrow(new IllegalArgumentException("Booking not found"));

        // Act
        ResponseEntity<Object> response = bookingController.getBookingByCustomerIdAndTourId(customerId, tourId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Booking not found", response.getBody());
        verify(bookingService, times(1)).getBookingByCustomerIdAndTourId(customerId, tourId);
    }

    @Test
    void cancelBookingByBookingId_ValidId_ReturnsOk() {
        // Arrange
        String bookingId = UUID.randomUUID().toString();
        Booking booking = new Booking();
        when(bookingService.cancelBookingByBookingId(bookingId)).thenReturn(booking);

        // Act
        ResponseEntity<Object> response = bookingController.cancelBookingByBookingId(bookingId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(booking, response.getBody());
        verify(bookingService, times(1)).cancelBookingByBookingId(bookingId);
    }

    @Test
    void cancelBookingByBookingId_InvalidId_ReturnsNotFound() {
        // Arrange
        String bookingId = UUID.randomUUID().toString();
        when(bookingService.cancelBookingByBookingId(bookingId)).thenThrow(new IllegalArgumentException("Booking not found"));

        // Act
        ResponseEntity<Object> response = bookingController.cancelBookingByBookingId(bookingId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Booking not found", response.getBody());
        verify(bookingService, times(1)).cancelBookingByBookingId(bookingId);
    }

    @Test
    void getBookingsByCustomerId_ValidId_ReturnsOk() {
        // Arrange
        String customerId = UUID.randomUUID().toString();
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        when(bookingService.getBookingsByCustomerId(customerId)).thenReturn(bookings);

        // Act
        ResponseEntity<Object> response = bookingController.getBookingsByCustomerId(customerId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookings, response.getBody());
        verify(bookingService, times(1)).getBookingsByCustomerId(customerId);
    }

    @Test
    void getBookingsByCustomerId_InvalidId_ReturnsNotFound() {
        // Arrange
        String customerId = UUID.randomUUID().toString();
        when(bookingService.getBookingsByCustomerId(customerId)).thenThrow(new IllegalArgumentException("Customer not found"));

        // Act
        ResponseEntity<Object> response = bookingController.getBookingsByCustomerId(customerId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer not found", response.getBody());
        verify(bookingService, times(1)).getBookingsByCustomerId(customerId);
    }
} 