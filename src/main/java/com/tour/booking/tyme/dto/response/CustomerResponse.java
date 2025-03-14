package com.tour.booking.tyme.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Jacksonized
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {

    private String id;
    private String name;
    private String email;
}
