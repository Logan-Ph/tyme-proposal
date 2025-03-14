package com.tour.booking.tyme.service.Payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tour.booking.tyme.entity.Payment;
import com.tour.booking.tyme.entity.Tour;
import com.tour.booking.tyme.entity.Voucher;
import com.tour.booking.tyme.repository.PaymentRepository;
import com.tour.booking.tyme.service.Discount.DiscountStrategy;
import com.tour.booking.tyme.service.Discount.DiscountStrategyFactory;

import jakarta.transaction.Transactional;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private DiscountStrategyFactory discountStrategyFactory;

    @Transactional
    public Payment processPayment(String bookingId, Voucher voucher, Tour tour, String customerId) {
        BigDecimal finalAmount = calculateFinalAmount(tour.getPrice(), voucher);

        Payment payment = Payment.builder()
            .bookingId(bookingId)
            .amount(finalAmount)
            .paymentDate(LocalDateTime.now())
            .status(PaymentStatus.SUCCESS)
            .customerId(customerId)
            .build();

        ;
        return paymentRepository.save(payment);
    }

    public Page<Payment> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    public List<Payment> getPaymentsByCustomerId(String customerId) {
        return paymentRepository.findByCustomerId(customerId);
    }

    private BigDecimal calculateFinalAmount(BigDecimal originalPrice, Voucher voucher) {
        if (voucher == null) {
            return originalPrice;
        }

        DiscountStrategy discountStrategy = discountStrategyFactory.createStrategy(
            voucher.getDiscountType(), 
            voucher.getDiscountValue()
        );

        return discountStrategy.calculateDiscount(originalPrice);
    }
}