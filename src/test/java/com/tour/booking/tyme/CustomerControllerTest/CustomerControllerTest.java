package com.tour.booking.tyme.CustomerControllerTest;

import com.tour.booking.tyme.controller.CustomerController;
import com.tour.booking.tyme.dto.request.CustomerRequest;
import com.tour.booking.tyme.dto.response.CustomerResponse;
import com.tour.booking.tyme.entity.Customer;
import com.tour.booking.tyme.exception.CustomerException;
import com.tour.booking.tyme.mapper.CustomerMapper;
import com.tour.booking.tyme.service.Customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_ValidRequest_ReturnsCreated() {
        // Arrange
        CustomerRequest request = mock(CustomerRequest.class);
        Customer customer = new Customer();
        when(customerMapper.toEntity(request)).thenReturn(customer);

        // Act
        ResponseEntity<Object> response = customerController.createCustomer(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(customerService, times(1)).createCustomer(customer);
    }

    @Test
    void createCustomer_InvalidRequest_ReturnsBadRequest() {
        // Arrange
        CustomerRequest request = mock(CustomerRequest.class);
        when(customerMapper.toEntity(request)).thenThrow(new IllegalArgumentException());

        // Act
        ResponseEntity<Object> response = customerController.createCustomer(request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof CustomerException);
    }

    @Test
    void getCustomerById_ValidId_ReturnsOk() {
        // Arrange
        String id = UUID.randomUUID().toString();
        Customer customer = new Customer();
        CustomerResponse customerResponse = mock(CustomerResponse.class);
        when(customerService.getCustomerById(id)).thenReturn(customer);
        when(customerMapper.toDTO(customer)).thenReturn(customerResponse);

        // Act
        ResponseEntity<Object> response = customerController.getCustomerById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponse, response.getBody());
    }

    @Test
    void getCustomerById_InvalidId_ReturnsNotFound() {
        // Arrange
        String id = UUID.randomUUID().toString();
        when(customerService.getCustomerById(id)).thenThrow(new IllegalArgumentException());

        // Act
        ResponseEntity<Object> response = customerController.getCustomerById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof CustomerException);
    }

    @Test
    void getCustomerByEmail_ValidEmail_ReturnsOk() {
        // Arrange
        String email = "test@example.com";
        Customer customer = new Customer();
        CustomerResponse customerResponse = mock(CustomerResponse.class);
        when(customerService.getCustomerByEmail(email)).thenReturn(customer);
        when(customerMapper.toDTO(customer)).thenReturn(customerResponse);

        // Act
        ResponseEntity<Object> response = customerController.getCustomerByEmail(email);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerResponse, response.getBody());
    }

    @Test
    void getCustomerByEmail_InvalidEmail_ReturnsNotFound() {
        // Arrange
        String email = "invalid_email";
        when(customerService.getCustomerByEmail(email)).thenThrow(new IllegalArgumentException());

        // Act
        ResponseEntity<Object> response = customerController.getCustomerByEmail(email);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof CustomerException);
    }

    @Test
    void deleteCustomer_ValidId_ReturnsOk() {
        // Arrange
        String id = UUID.randomUUID().toString();

        // Act
        ResponseEntity<Object> response = customerController.deleteCustomer(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(customerService, times(1)).deleteCustomer(id);
    }

    @Test
    void deleteCustomer_InvalidId_ReturnsNotFound() {
        // Arrange
        String id = UUID.randomUUID().toString();
        doThrow(new IllegalArgumentException()).when(customerService).deleteCustomer(id);

        // Act
        ResponseEntity<Object> response = customerController.deleteCustomer(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof CustomerException);
    }

    @Test
    void getAllCustomers_ValidRequest_ReturnsOk() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<Customer> customers = Collections.singletonList(new Customer());
        Page<Customer> customerPage = new PageImpl<>(customers);
        when(customerService.getAllCustomers(pageable)).thenReturn(customerPage);

        // Act
        ResponseEntity<Page<CustomerResponse>> response = customerController.getAllCustomers(page, size);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(customerService, times(1)).getAllCustomers(pageable);
    }

    @Test
    void getAllCustomers_ServiceException_ReturnsInternalServerError() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        when(customerService.getAllCustomers(pageable)).thenThrow(new RuntimeException());

        // Act
        ResponseEntity<Page<CustomerResponse>> response = customerController.getAllCustomers(page, size);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
} 