package com.tour.booking.tyme.dto.response;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Jacksonized
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TourResponse {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer availability;
}