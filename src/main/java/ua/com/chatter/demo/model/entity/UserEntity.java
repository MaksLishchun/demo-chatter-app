package ua.com.chatter.demo.model.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.annotation.Nullable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import ua.com.chatter.demo.model.UserActivityStatus;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String dateOfBirth;
    private @Nullable String imageUrl;
    private LocalDateTime lastActiveTime;
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private UserActivityStatus status;


    @ManyToMany(mappedBy = "users")
    private Set<ChatEntity> chats;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MessageEntity> messages;


    public UserEntity(Long id, String firstName, String lastName, String email, String password, String phoneNumber, String dateOfBirth, String imageUrl, LocalDateTime lastActiveTime, UserActivityStatus status, Set<ChatEntity> chats, Set<MessageEntity> messages) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.imageUrl = imageUrl;
        this.lastActiveTime = lastActiveTime;
        this.status = status;
        this.chats = chats;
        this.messages = messages;
    }

    public UserEntity() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<ChatEntity> getChats() {
        return this.chats;
    }

    public void setChats(Set<ChatEntity> chats) {
        this.chats = chats;
    }

    public Set<MessageEntity> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<MessageEntity> messages) {
        this.messages = messages;
    }

    public UserActivityStatus getStatus() {
        return this.status;
    }

    public void setStatus(UserActivityStatus status) {
        this.status = status;
    }

    public Boolean isActive() {
        return this.isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    
}
