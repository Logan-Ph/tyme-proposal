package com.tour.booking.tyme.mapper;

import com.tour.booking.tyme.dto.request.CustomerRequest;
import com.tour.booking.tyme.dto.response.CustomerResponse;
import com.tour.booking.tyme.entity.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-14T16:25:14+0700",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer toEntity(CustomerRequest customerRequest) {
        if ( customerRequest == null ) {
            return null;
        }

        Customer.CustomerBuilder customer = Customer.builder();

        customer.email( customerRequest.getEmail() );
        customer.name( customerRequest.getName() );
        customer.password( customerRequest.getPassword() );

        return customer.build();
    }

    @Override
    public CustomerResponse toDTO(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerResponse.CustomerResponseBuilder customerResponse = CustomerResponse.builder();

        customerResponse.email( customer.getEmail() );
        customerResponse.id( customer.getId() );
        customerResponse.name( customer.getName() );

        return customerResponse.build();
    }

    @Override
    public List<CustomerResponse> toDTOs(List<Customer> customers) {
        if ( customers == null ) {
            return null;
        }

        List<CustomerResponse> list = new ArrayList<CustomerResponse>( customers.size() );
        for ( Customer customer : customers ) {
            list.add( toDTO( customer ) );
        }

        return list;
    }

    @Override
    public List<Customer> toEntities(List<CustomerRequest> customerRequests) {
        if ( customerRequests == null ) {
            return null;
        }

        List<Customer> list = new ArrayList<Customer>( customerRequests.size() );
        for ( CustomerRequest customerRequest : customerRequests ) {
            list.add( toEntity( customerRequest ) );
        }

        return list;
    }
}
