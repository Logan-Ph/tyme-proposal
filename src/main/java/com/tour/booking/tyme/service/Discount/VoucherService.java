package com.tour.booking.tyme.service.Discount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.tour.booking.tyme.entity.Voucher;
import com.tour.booking.tyme.repository.VoucherRepository;

@Service
public class VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    public Voucher createVoucher(Voucher voucher) {
        Voucher createdVoucher = voucherRepository.save(voucher);
        return createdVoucher;
    }

    public Page<Voucher> getAllVouchers(Pageable pageable) {
        return voucherRepository.findAll(pageable);
    }

    public Voucher getVoucherById(String id) {
        return voucherRepository.findById(id)
            .orElse(null);
    }

    public Voucher getVoucherByCode(String code) {
        return voucherRepository.findByCode(code)
            .orElse(null);
    }

    public Voucher updateVoucher(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    public void deleteVoucher(String id) {
        voucherRepository.deleteById(id);
    }

}
