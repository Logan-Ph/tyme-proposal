package com.tour.booking.tyme.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tour.booking.tyme.dto.request.CustomerRequest;
import com.tour.booking.tyme.dto.response.CustomerResponse;
import com.tour.booking.tyme.entity.Customer;

@Mapper(componentModel="spring")
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "membershipTier", ignore = true)
    Customer toEntity(CustomerRequest customerRequest);

    CustomerResponse toDTO(Customer customer);

    List<CustomerResponse> toDTOs(List<Customer> customers);

    List<Customer> toEntities(List<CustomerRequest> customerRequests);
}
