package ua.com.chatter.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.chatter.demo.auth.jwt.JwtTokenUtil;
import ua.com.chatter.demo.model.dto.AuthenticationRequest;
import ua.com.chatter.demo.model.dto.AuthenticationResponse;
import ua.com.chatter.demo.model.dto.ChatterDefaultResponse;
import ua.com.chatter.demo.model.dto.ErrorType;
import ua.com.chatter.demo.model.dto.RegistrationRequest;
import ua.com.chatter.demo.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest request) {
        try {
            authService.registerUser(request);

            return ResponseEntity
                .ok(new ChatterDefaultResponse(200, "User registered successfully!", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(new ChatterDefaultResponse(400, e.getMessage(), ErrorType.REGISTRATION));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getPhoneNumber(), authenticationRequest.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getPhoneNumber());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
