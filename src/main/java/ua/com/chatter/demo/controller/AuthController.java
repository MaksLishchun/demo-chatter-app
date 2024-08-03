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
import ua.com.chatter.demo.model.dto.ErrorResponse;
import ua.com.chatter.demo.model.dto.ErrorType;
import ua.com.chatter.demo.model.dto.auth.AuthenticationRequest;
import ua.com.chatter.demo.model.dto.auth.AuthenticationResponse;
import ua.com.chatter.demo.model.dto.auth.RegistrationRequest;
import ua.com.chatter.demo.service.AuthService;
import ua.com.chatter.demo.utils.exceptions.ExceptionUtils;

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
            
            return createAuthenticationToken(new AuthenticationRequest(request.getPhoneNumber(), request.getPassword()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(400, e.getMessage(), ExceptionUtils.getErrorType(e, ErrorType.REGISTRATION)));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            if (authService.existsByPhoneNumber(authenticationRequest.getPhoneNumber())) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getPhoneNumber(), authenticationRequest.getPassword()));

                final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getPhoneNumber());
                final String jwt = jwtTokenUtil.generateToken(userDetails);

                if (jwt == null || jwt.isEmpty()) {
                    return ResponseEntity.badRequest()
                            .body(new ErrorResponse(400, "JWT token is not created", ErrorType.JWT_GENERATION));
                }

                return ResponseEntity.ok(new AuthenticationResponse(jwt));
            }
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(400, "User not found", ErrorType.ENTITY_NOT_FOUND));
        } catch (RuntimeException exc) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(400, exc.getMessage(), ExceptionUtils.getErrorType(exc, ErrorType.LOGIN)));
        }
    }
}
