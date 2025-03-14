package com.tour.booking.tyme.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tour.booking.tyme.dto.request.VoucherRequest;
import com.tour.booking.tyme.dto.response.VoucherResponse;
import com.tour.booking.tyme.entity.Voucher;
import com.tour.booking.tyme.mapper.VoucherMapper;
import com.tour.booking.tyme.service.Discount.VoucherService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/vouchers")
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @Autowired
    private VoucherMapper voucherMapper;
    
    @PostMapping
    @Operation(summary = "Create a new voucher", description = "Create a new voucher with the provided details")
    public ResponseEntity<Object> createVoucher(@RequestBody VoucherRequest request) {
        try {
            Voucher voucher = voucherMapper.toEntity(request);

            if (voucherService.getVoucherByCode(voucher.getCode()) != null) {
                throw new IllegalArgumentException("Voucher code already exists");
            }

            voucherService.createVoucher(voucher);
            return new ResponseEntity<>("Voucher created successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a voucher by ID", description = "Get a voucher by ID")
    public ResponseEntity<Object> getVoucherById(@PathVariable @NotBlank @Size(min = 1, max = 255) String id) {
        try {
            Voucher voucher = voucherService.getVoucherById(id);
            return new ResponseEntity<>(voucherMapper.toDTO(voucher), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/code/{code}")
    @Operation(summary = "Get a voucher by code", description = "Get a voucher by code")
    public ResponseEntity<Object> getVoucherByCode(@PathVariable @NotBlank @Size(min = 1, max = 255) String code) {
        try {
            Voucher voucher = voucherService.getVoucherByCode(code);
            if (voucher == null) {
                return new ResponseEntity<>("Voucher not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(voucherMapper.toDTO(voucher), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Get all vouchers", description = "Get all vouchers by code")
    public ResponseEntity<Object> getAllVouchers(
        @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page must be greater than or equal to 0") int page,
        @RequestParam(defaultValue = "10") @Min(value = 1, message = "Size must be greater than 0") int size)
    {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Voucher> vouchers = voucherService.getAllVouchers(pageable);
            List<VoucherResponse> voucherResponses = vouchers.getContent().stream()
            .map(voucherMapper::toDTO)
            .collect(Collectors.toList());
            return new ResponseEntity<>(voucherResponses, HttpStatus.OK);
        } catch (PropertyReferenceException e) {
            return new ResponseEntity<>("Invalid request parameters", HttpStatus.BAD_REQUEST);
        }
    }
    
}
