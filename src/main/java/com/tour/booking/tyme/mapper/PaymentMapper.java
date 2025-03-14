package com.tour.booking.tyme.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.tour.booking.tyme.dto.response.PaymentResponse;
import com.tour.booking.tyme.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponse toDTO(Payment payment);
    List<PaymentResponse> toDTOs(List<Payment> payments);
}
