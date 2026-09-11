package com.mercury.auth;

import com.mercury.customer.Customer;
import com.mercury.customer.CustomerService;
import com.mercury.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(CustomerService customerService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Optional<Customer> customerOptional = customerService.getCustomerByEmail(loginRequest.getEmail());
        if (customerOptional.isEmpty()) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        Customer customer = customerOptional.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), customer.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        String token = jwtService.generateToken(customer.getId(), customer.getEmail(), customer.getRole().name());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setName(customer.getName());
        return loginResponse;
    }

}
