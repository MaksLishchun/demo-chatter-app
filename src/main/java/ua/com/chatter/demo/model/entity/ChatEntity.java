package ua.com.chatter.demo.model.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import ua.com.chatter.demo.model.ChatType;

@Entity
@Table(name = "chats")
public class ChatEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chatName;
    private String chatLogo;

    
    @ManyToMany
    @JoinTable(
      name = "chat_users",
      joinColumns = @JoinColumn(name = "chat_id"),
      inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> users;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<MessageEntity> messages;

    @Enumerated(EnumType.STRING)
    private ChatType type;

    private LocalDateTime lastMessageTime;
    private Long createdBy;

    public ChatEntity() {
    }

    public ChatEntity(Long id, String chatName, String chatLogo, ChatType type, LocalDateTime lastMessageTime, Long createdBy) {
        this.id = id;
        this.chatName = chatName;
        this.chatLogo = chatLogo;
        this.type = type;
        this.lastMessageTime = lastMessageTime;
        this.createdBy = createdBy;
    }


    public ChatEntity(Long id, String chatName, String chatLogo, Set<UserEntity> users, Set<MessageEntity> messages, ChatType type, LocalDateTime lastMessageTime, Long createdBy) {
        this.id = id;
        this.chatName = chatName;
        this.chatLogo = chatLogo;
        this.users = users;
        this.messages = messages;
        this.type = type;
        this.lastMessageTime = lastMessageTime;
        this.createdBy = createdBy;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatName() {
        return this.chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatLogo() {
        return this.chatLogo;
    }

    public void setChatLogo(String chatLogo) {
        this.chatLogo = chatLogo;
    }

    public Set<UserEntity> getUsers() {
        return this.users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public void addUser(UserEntity user) {
        if(this.users == null) {
            this.users = new HashSet<>();
        }
        this.users.add(user);
    }

    public void addUsers(List<UserEntity> users) {
        if(this.users == null) {
            this.users = new HashSet<>();
        }
        this.users.addAll(users);
    }

    public Set<MessageEntity> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<MessageEntity> messages) {
        this.messages = messages;
    }

    public ChatType getType() {
        return this.type;
    }

    public void setType(ChatType type) {
        this.type = type;
    }

    public LocalDateTime getLastMessageTime() {
        return this.lastMessageTime;
    }

    public void setLastMessageTime(LocalDateTime lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }


}
