package ua.com.chatter.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ua.com.chatter.demo.model.dto.RegistrationRequest;
import ua.com.chatter.demo.model.entity.UserEntity;
import ua.com.chatter.demo.repository.UserRepository;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository chatterUserRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = chatterUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegistrationRequest request) {
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new RuntimeException("Phone number is already taken!");
        }

        UserEntity user = new UserEntity();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setImageUrl(request.getImageUrl());
        user.setLastActiveTime(LocalDateTime.now());

        userRepository.save(user);
    }

    public boolean loginUser(String phoneNumber, String password) {
        log.info("Login: phoneNumber -- " + phoneNumber);
        UserEntity user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found with phoneNumber: " + phoneNumber));

         log.info("Login: user name -- " + user.getFirstName());
        return passwordEncoder.matches(password, user.getPassword());
    }
}
