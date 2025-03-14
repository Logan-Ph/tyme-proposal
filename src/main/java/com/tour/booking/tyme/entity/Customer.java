package com.tour.booking.tyme.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

import com.tour.booking.tyme.service.MembershipTier.MembershipTierType;

@Entity
@Table(name = "customers")
@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.UUID)
	String id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "email", nullable = false, unique = true)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    
    @Column(name = "membership_tier", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    MembershipTierType membershipTier;

    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL)
	@ToString.Exclude
	List<Booking> bookings;
}
