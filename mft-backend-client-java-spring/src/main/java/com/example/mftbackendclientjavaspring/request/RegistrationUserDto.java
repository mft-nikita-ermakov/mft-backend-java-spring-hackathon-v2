package com.example.mftbackendclientjavaspring.request;

import lombok.Data;
@Data
public class RegistrationUserDto {
    private Long id;
    private String username;
    private String password;
    private String confirmPassword;
    private String role;
}
