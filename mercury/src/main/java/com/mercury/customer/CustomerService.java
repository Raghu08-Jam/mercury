package com.mercury.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = CustomerMapper.toEntity(request);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRole(Role.CUSTOMER);
        CustomerResponse response = CustomerMapper.toResponse(customerRepository.save(customer));
        logger.atInfo().log("Customer created with ID: {}", response.getId());
        return response;
    }

    public Optional<CustomerResponse> getCustomerById(Long id) {
        logger.atInfo().log("fetching customer with ID: {} [an admin only feature]", id);
        return customerRepository.findById(id)
                .map(CustomerMapper::toResponse);

    }

    public List<CustomerResponse> getAllCustomers() {
        logger.atInfo().log("fetching all customers[an admin only feature]");
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

}
