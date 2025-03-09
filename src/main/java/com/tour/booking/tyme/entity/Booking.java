package com.tour.booking.tyme.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "bookings")
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	@Schema(hidden = true)
	private String id;

	@Column(name = "customer_id", nullable = false)
	private String customerId;

	@Column(name = "tour_id", nullable = false)
	private String tourId;

	@Column(name = "booking_date", nullable = false)
	private LocalDateTime bookingDate;

	@Column(name = "status", nullable = false, length = 50)
	private String status;

	@Column(name = "voucher_id")
	private String voucherId;

	@Builder
	public Booking(String customerId, String tourId, LocalDateTime bookingDate, String status, String voucherId) {
		this.customerId = customerId;
		this.tourId = tourId;
		this.bookingDate = bookingDate;
		this.status = status;
		this.voucherId = voucherId;
	}
}
