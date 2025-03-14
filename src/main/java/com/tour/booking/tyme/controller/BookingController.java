package com.tour.booking.tyme.controller;

import com.tour.booking.tyme.entity.Booking;
import com.tour.booking.tyme.service.Booking.BookingService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@RestController
@RequestMapping("/bookings")

public class BookingController {
    @Autowired
    private BookingService bookingService;

    @GetMapping
    @Operation(summary = "Get all bookings", description = "Retrieve all bookings")
    public ResponseEntity<Object> getAllBookings() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a booking by ID", description = "Retrieve a booking by its unique identifier")
    public ResponseEntity<Object> getBookingById(@PathVariable String id) {
        Booking booking = bookingService.getBookingById(id);
        if (booking != null) {
            return new ResponseEntity<>(booking, HttpStatus.OK);
        }
        return new ResponseEntity<>("Booking not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @Operation(summary = "Create a new booking", description = "Create a new booking with the provided details")
    public ResponseEntity<Object> createBooking(@RequestParam String customerId, @RequestParam String tourId, @RequestParam(required = false) String voucherCode) {
        try {
            Booking booking;
            if (voucherCode != null) {
                booking = bookingService.createBooking(customerId, tourId, voucherCode);
            } else {
                booking = bookingService.createBooking(customerId, tourId);
            }

            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // get booking by customer id and tour id
    @GetMapping("/customer/{customerId}/tour/{tourId}")
    @Operation(summary = "Get a booking by customer ID and tour ID", description = "Retrieve a booking by customer ID and tour ID")
    public ResponseEntity<Object> getBookingByCustomerIdAndTourId(
        @PathVariable @NotBlank @Size(min = 24, max = 128) String customerId,
        @PathVariable @NotBlank @Size(min = 24, max = 128) String tourId) {
        try {
            Booking booking = bookingService.getBookingByCustomerIdAndTourId(customerId, tourId);
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // cancel booking by booking id
    @PutMapping("/{bookingId}")
    @Operation(summary = "Cancel a booking by booking ID", description = "Cancel a booking by booking ID")
    public ResponseEntity<Object> cancelBookingByBookingId(
        @PathVariable @NotBlank @Size(min = 24, max = 128) String bookingId) {
        try {
            Booking booking = bookingService.cancelBookingByBookingId(bookingId);
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // get booking by customer id
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get bookings by customer ID", description = "Retrieve bookings by customer ID")
    public ResponseEntity<Object> getBookingsByCustomerId(
        @PathVariable @NotBlank @Size(min = 24, max = 128) String customerId) {
        try {
            List<Booking> bookings = bookingService.getBookingsByCustomerId(customerId);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
