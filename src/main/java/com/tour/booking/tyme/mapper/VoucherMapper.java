package com.tour.booking.tyme.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tour.booking.tyme.dto.request.VoucherRequest;
import com.tour.booking.tyme.dto.response.VoucherResponse;
import com.tour.booking.tyme.entity.Voucher;

@Mapper(componentModel = "spring")
public interface VoucherMapper {

    VoucherResponse toDTO(Voucher voucher);

    @Mapping(target = "id", ignore = true)
    Voucher toEntity(VoucherRequest request);
}
