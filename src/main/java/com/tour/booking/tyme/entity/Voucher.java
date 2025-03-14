package com.tour.booking.tyme.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.tour.booking.tyme.service.Discount.DiscountType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "vouchers")
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "code", length = 50)
    private String code;

    @Column(name = "discount_type",length = 50)
    @Enumerated(EnumType.STRING)
    DiscountType discountType;

    @Column(name = "discount_value", precision = 19, scale = 2)
    BigDecimal discountValue;

    @Column(name = "expiration_date")
    LocalDate expirationDate;
}
