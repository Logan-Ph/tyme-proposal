package com.tour.booking.tyme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.tour.booking.tyme.service.Booking.BookingStatus;

import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@Entity
@Table(name = "bookings")
@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	String id;

	@Column(name = "customer_id", nullable = false)
	String customerId;

	@Column(name = "tour_id", nullable = false)
	String tourId;

	@Column(name = "booking_date", nullable = false)
	LocalDateTime bookingDate;

	@Column(name = "status", nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	BookingStatus status;

	@Column(name = "voucher_id")
	String voucherId;
}
