package com.tour.booking.tyme.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "payments")
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Payment {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	@Schema(hidden = true)
	private String id;

	@Column(name = "booking_id", nullable = false)
	private String bookingId;

	@Column(name = "amount", nullable = false, precision = 19, scale = 2)
	private BigDecimal amount;

	@Column(name = "payment_date", nullable = false)
	private LocalDateTime paymentDate;

	@Column(name = "status", nullable = false, length = 50)
	private String status;

	@Builder
	public Payment(String bookingId, BigDecimal amount, LocalDateTime paymentDate, String status) {
		this.bookingId = bookingId;
		this.amount = amount;
		this.paymentDate = paymentDate;
		this.status = status;
	}
}
