package com.tour.booking.tyme.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import com.tour.booking.tyme.service.MembershipTier.MembershipTierType;

@Entity
@Getter
@Setter
@ToString
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    
    @Column(name = "membership_tier", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private MembershipTierType membershipTier;

    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL)
	@ToString.Exclude
	private List<Booking> bookings;
}
