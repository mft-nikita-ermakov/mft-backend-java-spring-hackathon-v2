package com.example.mftbackendclientjavaspring.controller;

import com.example.mftbackendclientjavaspring.request.JwtRequest;
import com.example.mftbackendclientjavaspring.request.RegistrationUserDto;
import com.example.mftbackendclientjavaspring.service.AuthService;
import com.example.mftbackendclientjavaspring.service.FillWebService;
import com.example.mftbackendclientjavaspring.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/main") //URI - resource ID
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private AuthService authService;
    @Autowired
    private FillWebService fillWebService;
    @Autowired
    private UserService userService;

    @Value("${eureka.instance.instance-id}")
    private String id;

    @GetMapping("/client")
    public String getClient() {
        logger.info("Received request to view id instance");
        return id;
    }

    @PostMapping("/authorize")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        logger.info("Received request to create auth token");
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        logger.info("Received request to create new user");
        fillWebService.FillTables();
        return authService.createNewUser(registrationUserDto);
    }


    @PostMapping("/decode")
    public String decodeToken(HttpServletRequest request) {
        logger.info("Received request get the token from the request header");
        String token = request.getHeader("Authorization").substring(7);
        return userService.decodeToken(token);
    }


}
