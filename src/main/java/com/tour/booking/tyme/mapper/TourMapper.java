package com.tour.booking.tyme.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tour.booking.tyme.dto.TourDTO;
import com.tour.booking.tyme.entity.Tour;

@Mapper(componentModel = "spring")
public interface TourMapper {
    TourDTO toDTO(Tour tour);

    @Mapping(target = "bookings", ignore = true)
    Tour toEntity(TourDTO tourDTO);

    @Mapping(target = "bookings", ignore = true)
    List<TourDTO> toDTOs(List<Tour> tours);
}
