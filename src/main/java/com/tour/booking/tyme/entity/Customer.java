package com.tour.booking.tyme.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Getter
@Setter
@ToString
@Table(name = "customers")
@NoArgsConstructor
@Builder
public class Customer {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	@Schema(hidden = true)
	private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "membership_tier", nullable = false, length = 50)
    private String membershipTier;

    // Relationships
	@OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Booking> bookings;

    @Builder
    public Customer(String name, String email, String password, String membershipTier) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.membershipTier = membershipTier;
    }
}
