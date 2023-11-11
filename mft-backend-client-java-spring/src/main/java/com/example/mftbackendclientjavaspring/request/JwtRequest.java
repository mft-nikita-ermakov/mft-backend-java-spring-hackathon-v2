package com.example.mftbackendclientjavaspring.request;

import lombok.Data;
@Data
public class JwtRequest {
    private String username;
    private String password;
}

