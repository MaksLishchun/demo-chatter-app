package ua.com.chatter.demo.service;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Lazy;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ua.com.chatter.demo.model.dto.auth.RegistrationRequest;
import ua.com.chatter.demo.model.entity.UserEntity;
import ua.com.chatter.demo.repository.UserRepository;
import ua.com.chatter.demo.utils.exceptions.EntityIsExistException;
import ua.com.chatter.demo.utils.exceptions.EntityIsNullException;
import ua.com.chatter.demo.utils.exceptions.EntityNotFoundException;
import ua.com.chatter.demo.utils.exceptions.auth.UserEmailIsExistException;
import ua.com.chatter.demo.utils.exceptions.auth.UserPhoneIsExistException;

@Service
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository chatterUserRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = chatterUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegistrationRequest request) {
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new UserPhoneIsExistException("Phone number is already taken!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserEmailIsExistException("Email is already taken!");
        }

        UserEntity user = new UserEntity();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(request.getDateOfBirth());
        // user.setImageUrl(request.getImageUrl());
        user.setLastActiveTime(LocalDateTime.now());

        try {
            UserEntity userEntity = userRepository.save(user);
            if (userEntity == null) {
                throw new EntityNotFoundException("User is not created");
            }
        } catch (IllegalArgumentException e) {
            throw new EntityIsNullException("User can not be null");
        } catch (OptimisticLockingFailureException e) {
            throw new EntityIsExistException("User is exist");
        }

    }

    public boolean loginUser(String phoneNumber, String password) {
        UserEntity user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("User not found with phoneNumber: " + phoneNumber));

        return passwordEncoder.matches(password, user.getPassword());
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }
}
