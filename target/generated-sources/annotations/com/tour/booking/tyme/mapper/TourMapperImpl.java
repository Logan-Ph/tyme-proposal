package com.tour.booking.tyme.mapper;

import com.tour.booking.tyme.dto.TourDTO;
import com.tour.booking.tyme.entity.Tour;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-11T17:22:22+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class TourMapperImpl implements TourMapper {

    @Override
    public TourDTO toDTO(Tour tour) {
        if ( tour == null ) {
            return null;
        }

        TourDTO.TourDTOBuilder tourDTO = TourDTO.builder();

        tourDTO.availability( tour.getAvailability() );
        tourDTO.category( tour.getCategory() );
        tourDTO.description( tour.getDescription() );
        tourDTO.id( tour.getId() );
        tourDTO.name( tour.getName() );
        tourDTO.price( tour.getPrice() );

        return tourDTO.build();
    }

    @Override
    public Tour toEntity(TourDTO tourDTO) {
        if ( tourDTO == null ) {
            return null;
        }

        Tour.TourBuilder tour = Tour.builder();

        tour.availability( tourDTO.getAvailability() );
        tour.category( tourDTO.getCategory() );
        tour.description( tourDTO.getDescription() );
        tour.id( tourDTO.getId() );
        tour.name( tourDTO.getName() );
        tour.price( tourDTO.getPrice() );

        return tour.build();
    }

    @Override
    public List<TourDTO> toDTOs(List<Tour> tours) {
        if ( tours == null ) {
            return null;
        }

        List<TourDTO> list = new ArrayList<TourDTO>( tours.size() );
        for ( Tour tour : tours ) {
            list.add( toDTO( tour ) );
        }

        return list;
    }
}
