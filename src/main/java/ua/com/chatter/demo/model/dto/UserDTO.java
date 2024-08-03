package ua.com.chatter.demo.model.dto;

import java.time.LocalDateTime;

import javax.annotation.Nullable;

import ua.com.chatter.demo.model.UserActivityStatus;

public class UserDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private @Nullable String imageUrl;
    private LocalDateTime lastActiveTime;
    private UserActivityStatus status;


    public UserDTO() {
    }

    public UserDTO(Long userId, String firstName, String lastName, String email, String phoneNumber, String dateOfBirth, String imageUrl, LocalDateTime lastActiveTime, UserActivityStatus status) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.imageUrl = imageUrl;
        this.lastActiveTime = lastActiveTime;
        this.status = status;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDateTime getLastActiveTime() {
        return this.lastActiveTime;
    }

    public void setLastActiveTime(LocalDateTime lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public UserActivityStatus getStatus() {
        return this.status;
    }

    public void setStatus(UserActivityStatus status) {
        this.status = status;
    }

}
