package ua.com.chatter.demo.model.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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


    public ChatEntity() {
    }

    public ChatEntity(Long id, String chatName, String chatLogo) {
        this.id = id;
        this.chatName = chatName;
        this.chatLogo = chatLogo;
    }

    public ChatEntity(Long chatId, String chatName, String chatLogo, Set<UserEntity> users, Set<MessageEntity> messages) {
        this.id = chatId;
        this.chatName = chatName;
        this.chatLogo = chatLogo;
        this.users = users;
        this.messages = messages;
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

    public Set<MessageEntity> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<MessageEntity> messages) {
        this.messages = messages;
    }

}
