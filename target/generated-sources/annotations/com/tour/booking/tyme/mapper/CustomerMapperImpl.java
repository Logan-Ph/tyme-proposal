package com.tour.booking.tyme.mapper;

import com.tour.booking.tyme.dto.CustomerCreationDTO;
import com.tour.booking.tyme.dto.CustomerDTO;
import com.tour.booking.tyme.entity.Customer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-11T15:21:37+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerDTO toDTO(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDTO.CustomerDTOBuilder customerDTO = CustomerDTO.builder();

        customerDTO.email( customer.getEmail() );
        customerDTO.id( customer.getId() );
        customerDTO.name( customer.getName() );

        return customerDTO.build();
    }

    @Override
    public Customer toEntity(CustomerDTO customerDTO) {
        if ( customerDTO == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.email( customerDTO.getEmail() );
        customer.id( customerDTO.getId() );
        customer.name( customerDTO.getName() );

        return customer.build();
    }

    @Override
    public Customer toEntity(CustomerCreationDTO customerCreationDTO) {
        if ( customerCreationDTO == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.email( customerCreationDTO.getEmail() );
        customer.name( customerCreationDTO.getName() );

        return customer.build();
    }
}
