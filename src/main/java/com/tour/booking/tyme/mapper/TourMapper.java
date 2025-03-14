package com.tour.booking.tyme.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tour.booking.tyme.dto.request.TourRequest;
import com.tour.booking.tyme.dto.response.TourResponse;
import com.tour.booking.tyme.entity.Tour;

@Mapper(componentModel = "spring")
public interface TourMapper {
    TourResponse toDTO(Tour tour);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "availability", ignore = true)
    @Mapping(target = "category", ignore = true)
    Tour toEntity(TourResponse tourResponse);

    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "id", ignore = true)
    Tour toEntity(TourRequest tourRequest);

    @Mapping(target = "bookings", ignore = true)
    List<TourResponse> toDTOs(List<Tour> tours);

    @Mapping(target = "bookings", ignore = true)
    List<Tour> toEntities(List<TourRequest> tourRequests);
}
