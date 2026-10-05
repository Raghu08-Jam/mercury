package com.mercury.customer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Captor
    private ArgumentCaptor<Customer> customerCaptor;

    @Test
    void createCustomer_shouldHashPasswordAndSaveCustomer() {
        CustomerRequest requestIn = new CustomerRequest();
        requestIn.setName("Test User");
        requestIn.setEmail("TestMail@mail.com");
        requestIn.setPassword("TestPassword");
        requestIn.setPhoneNumber("1234567890");
        requestIn.setAddress("Test Address");
        when(passwordEncoder.encode("TestPassword")).thenReturn("TestPassword123");
        Customer requestOut = new Customer();
        requestOut.setId(1L);
        requestOut.setName("Test User");
        requestOut.setEmail("TestMail@mail.com");
        requestOut.setPassword("TestPassword123");
        requestOut.setPhoneNumber("1234567890");
        requestOut.setAddress("Test Address");
        when(customerRepository.save(customerCaptor.capture())).thenReturn(requestOut);
        CustomerResponse response = customerService.createCustomer(requestIn);
        Customer capturedCustomer = customerCaptor.getValue();
        assertEquals(requestOut.getId(), response.getId());
        assertEquals(requestOut.getName(), capturedCustomer.getName());
        assertEquals(requestOut.getEmail(), capturedCustomer.getEmail());
        assertEquals(requestOut.getPassword(), capturedCustomer.getPassword());
        assertEquals(requestOut.getPhoneNumber(), capturedCustomer.getPhoneNumber());
        assertEquals(requestOut.getAddress(), capturedCustomer.getAddress());
    }

    @Test
    void getCustomerById_shouldReturnCustomerResponse() {
        Long customerId = 1L;
        Customer customerIn = new Customer();
        customerIn.setId(customerId);
        customerIn.setName("Test User");
        customerIn.setEmail("TestMail@mail.com");
        customerIn.setPassword("TestPassword");
        customerIn.setPhoneNumber("1234567890");
        customerIn.setAddress("Test Address");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customerIn));
        Optional<CustomerResponse> response = customerService.getCustomerById(customerId);
        assertTrue(response.isPresent());
        assertEquals(customerIn.getId(), response.get().getId());
        assertEquals(customerIn.getName(), response.get().getName());
        assertEquals(customerIn.getEmail(), response.get().getEmail());
        assertEquals(customerIn.getPhoneNumber(), response.get().getPhoneNumber());
        assertEquals(customerIn.getAddress(), response.get().getAddress());
    }
    @Test
    void getCustomerById_shouldReturnEmptyOptionalWhenCustomerNotFound() {
        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
        Optional<CustomerResponse> response = customerService.getCustomerById(customerId);
        assertTrue(response.isEmpty());
    }

}