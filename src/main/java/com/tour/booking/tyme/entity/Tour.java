package com.tour.booking.tyme.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

import com.tour.booking.tyme.service.Tour.TourCategory;

@Entity
@Table(name = "tours")
@Builder
@Jacksonized
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tour {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;

	@Column(name = "name", nullable = false)
	String name;

	@Column(name = "description")
	String description;

	@Column(name = "price", nullable = false, precision = 19, scale = 2)
	BigDecimal price;

	@Column(name = "availability", nullable = false)
	Integer availability;

	@Enumerated(EnumType.STRING)
	@Column(name = "category", nullable = false, length = 50)
	TourCategory category;

	// Relationships
	@OneToMany(mappedBy = "tourId", cascade = CascadeType.ALL)
	List<Booking> bookings;
}