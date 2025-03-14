package com.tour.booking.tyme.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tour.booking.tyme.entity.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, String> {
    Optional<Voucher> findByCode(String code);
}
