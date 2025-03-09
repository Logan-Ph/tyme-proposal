package com.tour.booking.tyme.entity;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tours")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tour {
    @Id
    @Column(name = "id", length = 255)
    private String id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "availability", nullable = false)
    private Integer availability;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    // Relationships
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Booking> bookings;
}
