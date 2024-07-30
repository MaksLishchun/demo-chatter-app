package ua.com.chatter.demo.model.dto.message;

import java.time.LocalDateTime;

public class MessageRequest {

    private Long id;
    private Long userId;
    private Long chatId;
    private LocalDateTime createdAt;
    private String content;

    public MessageRequest() {}

    public MessageRequest(LocalDateTime createdAt, String content) {
        this.createdAt = createdAt;
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getChatId() {
        return this.chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
    
}
