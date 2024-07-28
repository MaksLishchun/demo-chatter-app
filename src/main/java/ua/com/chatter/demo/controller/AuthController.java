package ua.com.chatter.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.chatter.demo.auth.AuthService;
import ua.com.chatter.demo.model.dto.ChattyDefaultResponse;
import ua.com.chatter.demo.model.dto.ErrorType;
import ua.com.chatter.demo.model.dto.LoginRequest;
import ua.com.chatter.demo.model.dto.RegistrationRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {
        try {
            authService.registerUser(request);

            return ResponseEntity
                .ok(new ChattyDefaultResponse(200, "User registered successfully!", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new ChattyDefaultResponse(400, e.getMessage(), ErrorType.REGISTRATION));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        if (authService.loginUser(request.getPhoneNumber(), request.getPassword())) {
            return ResponseEntity.ok("User logged in successfully!");
        } else {
            return ResponseEntity.badRequest().body("Invalid phoneNubber or password!");
        }
    }
}
