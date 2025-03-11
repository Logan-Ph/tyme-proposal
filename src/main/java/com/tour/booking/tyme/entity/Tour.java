package com.tour.booking.tyme.entity;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

import com.tour.booking.tyme.service.Tour.TourCategory;

@Entity
@Table(name = "tours")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tour {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price", nullable = false, precision = 19, scale = 2)
	private BigDecimal price;

	@Column(name = "availability", nullable = false)
	private Integer availability;

	@Enumerated(EnumType.STRING)
	@Column(name = "category", nullable = false, length = 50)
	private TourCategory category;

	// Relationships
	@OneToMany(mappedBy = "tourId", cascade = CascadeType.ALL)
	private List<Booking> bookings;
}