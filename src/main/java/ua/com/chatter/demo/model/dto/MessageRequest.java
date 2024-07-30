package ua.com.chatter.demo.model.dto;

import java.time.LocalDateTime;

public class MessageRequest {

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
}
