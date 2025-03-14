package com.tour.booking.tyme.service.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tour.booking.tyme.entity.Booking;
import com.tour.booking.tyme.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.tour.booking.tyme.entity.Customer;
import com.tour.booking.tyme.entity.Tour;
import com.tour.booking.tyme.entity.Voucher;
import com.tour.booking.tyme.service.Tour.TourService;
import com.tour.booking.tyme.service.Customer.CustomerService;
import com.tour.booking.tyme.service.Discount.VoucherService;
import com.tour.booking.tyme.service.Payment.PaymentService;

import jakarta.transaction.Transactional;
@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TourService tourService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VoucherService voucherService;

    @Autowired
    private PaymentService paymentService;

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
    public Booking createBooking(String customerId, String tourId) {
        Customer customer = customerService.getCustomerById(customerId);

        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        Tour tour = tourService.getTourById(tourId);

        if (tour == null) {
            throw new IllegalArgumentException("Tour not found");
        }

        if (tour.getAvailability() <= 0) {
            throw new IllegalArgumentException("Tour is not available");
        }

        Booking booking = bookingRepository.findByCustomerIdAndTourId(customerId, tourId)
            .orElse(null);

        if (booking != null && booking.getStatus() != BookingStatus.CANCELED) {
            throw new IllegalArgumentException("Booking already exists");
        }

                // Create booking
                Booking newBooking = Booking.builder()
                .customerId(customerId)
                .tourId(tourId)
                .voucherId(null)
                .status(BookingStatus.CONFIRMED)
                .bookingDate(LocalDateTime.now())
                .build();
    
            // Update tour availability
            tour.setAvailability(tour.getAvailability() - 1);
            tourService.updateTour(tour);
    
            Booking savedBooking = bookingRepository.save(newBooking);
    
            paymentService.processPayment(savedBooking.getId(), null, tour, customerId);
    
            return savedBooking;
    }
    @Transactional
    public Booking createBooking(String customerId, String tourId, String code) {
        Customer customer = customerService.getCustomerById(customerId);

        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        Tour tour = tourService.getTourById(tourId);

        if (tour == null) {
            throw new IllegalArgumentException("Tour not found");
        }

        if (tour.getAvailability() <= 0) {
            throw new IllegalArgumentException("Tour is not available");
        }

        Booking booking = bookingRepository.findByCustomerIdAndTourId(customerId, tourId)
            .orElse(null);

        if (booking != null && booking.getStatus() != BookingStatus.CANCELED) {
            throw new IllegalArgumentException("Booking already exists");
        }
        

        Voucher voucher = voucherService.getVoucherByCode(code);

        if (voucher == null) {
            throw new IllegalArgumentException("Voucher not found");
        }

        if (voucher.getExpirationDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Voucher expired");
        }

        // Create booking
        Booking newBooking = Booking.builder()
            .customerId(customerId)
            .tourId(tourId)
            .voucherId(voucher.getId())
            .status(BookingStatus.CONFIRMED)
            .bookingDate(LocalDateTime.now())
            .build();

        // Update tour availability
        tour.setAvailability(tour.getAvailability() - 1);
        tourService.updateTour(tour);

        Booking savedBooking = bookingRepository.save(newBooking);

        paymentService.processPayment(savedBooking.getId(), voucher, tour, customerId);

        return savedBooking;
    }

    @Transactional
    public Booking cancelBookingByBookingId(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (booking.getStatus() == BookingStatus.CANCELED) {
            throw new IllegalArgumentException("Booking already canceled");
        }

        Tour tour = tourService.getTourById(booking.getTourId());
        tour.setAvailability(tour.getAvailability() + 1);
        tourService.updateTour(tour);

        booking.setStatus(BookingStatus.CANCELED);
        return bookingRepository.save(booking );
    }
}
