package com.mercury.customer;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerResponse createCustomer(CustomerRequest request) {
        Customer customer = CustomerMapper.toEntity(request);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setRole(Role.CUSTOMER);
        return CustomerMapper.toResponse(customerRepository.save(customer));
    }

    public Optional<CustomerResponse> getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(CustomerMapper::toResponse);
    }

    public List<CustomerResponse> getAllCustomers() {
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
