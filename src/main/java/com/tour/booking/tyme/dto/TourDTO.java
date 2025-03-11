package com.tour.booking.tyme.dto;

import java.math.BigDecimal;

import com.tour.booking.tyme.service.Tour.TourCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TourDTO {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer availability;
    private TourCategory category;
}
