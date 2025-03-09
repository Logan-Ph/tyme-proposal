package com.tour.booking.tyme.entity;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

import com.tour.booking.tyme.service.Tour.TourCategory;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "tours")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
public class Tour {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	@Schema(hidden = true)
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
	@ToString.Exclude
	@Schema(hidden = true)
	private List<Booking> bookings;

	@Builder
	public Tour(String id, String name, String description, BigDecimal price, Integer availability, TourCategory category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.availability = availability;
		this.category = category;
	}

	
}