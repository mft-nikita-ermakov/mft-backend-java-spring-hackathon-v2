package com.example.mftbackendclientjavaspring.request;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String role;

}
