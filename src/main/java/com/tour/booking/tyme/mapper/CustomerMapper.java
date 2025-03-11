package com.tour.booking.tyme.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.tour.booking.tyme.dto.CustomerCreationDTO;
import com.tour.booking.tyme.dto.CustomerDTO;
import com.tour.booking.tyme.entity.Customer;

@Mapper(componentModel="spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO toDTO(Customer customer);

    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "membershipTier", ignore = true)
    Customer toEntity(CustomerDTO customerDTO);

    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "membershipTier", ignore = true)
    @Mapping(target = "id", ignore = true)
    Customer toEntity(CustomerCreationDTO customerCreationDTO);
}
