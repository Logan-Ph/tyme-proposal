package com.tour.booking.tyme.service.Booking;

import java.time.LocalDateTime;

import com.tour.booking.tyme.entity.Booking;
import com.tour.booking.tyme.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.tour.booking.tyme.entity.Customer;
import com.tour.booking.tyme.entity.Tour;
import com.tour.booking.tyme.service.Tour.TourService;
import com.tour.booking.tyme.service.Customer.CustomerService;

import jakarta.transaction.Transactional;
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TourService tourService;

    @Autowired
    private CustomerService customerService;

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByCustomerId(String customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    public Booking getBookingById(String id) {
        return bookingRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    public Booking getBookingByCustomerIdAndTourId(String customerId, String tourId) {
        return bookingRepository.findByCustomerIdAndTourId(customerId, tourId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    @Transactional
    public Booking createBooking(String customerId, String tourId, String voucherId) {
        Customer customer = customerService.getCustomerById(customerId);
        Tour tour = tourService.getTourById(tourId);

        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        if (tour == null) {
            throw new IllegalArgumentException("Tour not found");
        }

        if (tour.getAvailability() <= 0) {
            throw new IllegalArgumentException("Tour is not available");
        }

        Booking booking = bookingRepository.findByCustomerIdAndTourId(customerId, tourId)
            .orElse(null);

        if (booking != null) {
            throw new IllegalArgumentException("Booking already exists");
        }

        // Create booking
        Booking newBooking = Booking.builder()
            .customerId(customerId)
            .tourId(tourId)
            // .voucherId(voucherId)
            .status(BookingStatus.CONFIRMED.name())
            .bookingDate(LocalDateTime.now())
            .build();

        // Update tour availability
        tour.setAvailability(tour.getAvailability() - 1);
        tourService.updateTour(tourId, tour);

        return bookingRepository.save(newBooking);
    }

    @Transactional
    public Booking cancelBookingByCustomerIdAndTourId(String customerId, String tourId) {
        Booking booking = bookingRepository.findByCustomerIdAndTourId(customerId, tourId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        Tour tour = tourService.getTourById(tourId);
        tour.setAvailability(tour.getAvailability() + 1);
        tourService.updateTour(tourId, tour);

        booking.setStatus(BookingStatus.CANCELLED.name());
        return bookingRepository.save(booking);
    }
}
