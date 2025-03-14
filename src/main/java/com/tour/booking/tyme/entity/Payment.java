package com.tour.booking.tyme.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tour.booking.tyme.service.Payment.PaymentStatus;

@Entity
@Table(name = "payments")
@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;

	@Column(name = "booking_id", nullable = false)
	String bookingId;

	@Column(name = "amount", nullable = false, precision = 19, scale = 2)
	BigDecimal amount;

	@Column(name = "payment_date", nullable = false)
	LocalDateTime paymentDate;

	@Column(name = "status", nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	PaymentStatus status;

	@Column(name = "customer_id", nullable = false)
	String customerId;
}
