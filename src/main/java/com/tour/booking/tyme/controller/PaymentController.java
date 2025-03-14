package com.tour.booking.tyme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tour.booking.tyme.service.Payment.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.tour.booking.tyme.entity.Payment;
import com.tour.booking.tyme.dto.response.PaymentResponse;
import com.tour.booking.tyme.mapper.PaymentMapper;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentMapper paymentMapper;

    @GetMapping
    @Operation(summary = "Get all payments", description = "Retrieve a list of all payments")
    public ResponseEntity<Object> getAllPayments(
        @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page must be greater than or equal to 0") int page,
        @RequestParam(defaultValue = "10") @Min(value = 1, message = "Size must be greater than 0") int size)
    {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Payment> payments = paymentService.getAllPayments(pageable);
            List<PaymentResponse> paymentsResponse = paymentMapper.toDTOs(payments.getContent());
            return new ResponseEntity<>(paymentsResponse, HttpStatus.OK);
        } catch (PropertyReferenceException e) {
            return new ResponseEntity<>("Invalid request parameters", HttpStatus.BAD_REQUEST);
        }
    }
    
    // Get payment by Customer ID
    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get payment by Customer ID", description = "Retrieve a payment by Customer ID")
    public ResponseEntity<Object> getPaymentByCustomerId(
        @PathVariable @Size(min = 24, max = 128, message = "Customer ID must be between 24 and 128 characters") String customerId) {
        try {
            List<Payment> payments = paymentService.getPaymentsByCustomerId(customerId);
            List<PaymentResponse> paymentsResponse = paymentMapper.toDTOs(payments);
            return new ResponseEntity<>(paymentsResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
