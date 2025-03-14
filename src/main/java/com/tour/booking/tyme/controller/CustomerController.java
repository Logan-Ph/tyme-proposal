package com.tour.booking.tyme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tour.booking.tyme.dto.request.CustomerRequest;
import com.tour.booking.tyme.dto.response.CustomerResponse;
import com.tour.booking.tyme.entity.Customer;
import com.tour.booking.tyme.exception.CustomerException;
import com.tour.booking.tyme.mapper.CustomerMapper;
import com.tour.booking.tyme.service.Customer.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping
    @Operation(summary = "Create a new customer", description = "Create a new customer with the provided details")
    public ResponseEntity<Object> createCustomer(
        @RequestBody @Validated CustomerRequest customerRequest
    ) {
        try {
            customerService.createCustomer(customerMapper.toEntity(customerRequest));
            return new ResponseEntity<>("Customer created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomerException(e.getLocalizedMessage(), "INTERNAL_SERVER_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a customer by ID", description = "Get a customer by their ID")
    public ResponseEntity<Object> getCustomerById(
        @PathVariable @NotBlank @Size(min = 24, max = 128) String id) {
        try {
            Customer customer = customerService.getCustomerById(id);
            CustomerResponse customerResponse = customerMapper.toDTO(customer);
            return new ResponseEntity<>(customerResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new CustomerException("Customer not found", "CUSTOMER_NOT_FOUND"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomerException("Internal server error", "INTERNAL_SERVER_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get a customer by email", description = "Get a customer by their email")
    public ResponseEntity<Object> getCustomerByEmail(
        @PathVariable @NotBlank @Email String email) {
        try {
            Customer customer = customerService.getCustomerByEmail(email);
            CustomerResponse customerResponse = customerMapper.toDTO(customer);
            return new ResponseEntity<>(customerResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new CustomerException("Customer not found", "CUSTOMER_NOT_FOUND"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomerException("Internal server error", "INTERNAL_SERVER_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer by ID", description = "Delete a customer by their ID")
    public ResponseEntity<Object> deleteCustomer(
        @PathVariable @NotBlank @Size(min = 24, max = 128) String id) {
        try {
            customerService.deleteCustomer(id);
            return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new CustomerException("Customer not found", "CUSTOMER_NOT_FOUND"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomerException("Internal server error", "INTERNAL_SERVER_ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get all customers with pagination", description = "Get a paginated list of all customers")
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<CustomerResponse> customerResponses = customerService.getAllCustomers(pageable)
                .map(customerMapper::toDTO);
            return new ResponseEntity<>(customerResponses, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
