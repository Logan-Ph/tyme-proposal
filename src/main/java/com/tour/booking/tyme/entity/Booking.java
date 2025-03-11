package com.tour.booking.tyme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
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
	String status;

	@Column(name = "voucher_id")
	String voucherId;
}
