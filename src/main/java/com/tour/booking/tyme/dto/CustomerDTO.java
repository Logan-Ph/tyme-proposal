package com.tour.booking.tyme.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "Customer DTO")
public class CustomerDTO {

    private String id;
    private String name;
    private String email;
}
