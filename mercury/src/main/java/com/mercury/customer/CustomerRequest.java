package com.mercury.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CustomerRequest {
    @NotBlank(message = "name cannot be blank")
    private String name;

    @Email(message = "email must be a valid email address")
    private String email;

    @NotBlank(message = "phone number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "phone number must be a valid phone number")
    private String phoneNumber;

    @NotBlank(message = "address cannot be blank")
    private String address;

    @NotBlank(message = "password cannot be blank")
    @Size(min = 8, message = "password must be at least 8 characters long")
    private String password;

    public CustomerRequest() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
