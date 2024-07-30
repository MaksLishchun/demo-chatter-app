package ua.com.chatter.demo.model.dto;

import java.time.LocalDateTime;

import ua.com.chatter.demo.utils.GsonFormatter;

public class MessageDTO {

    private Long messageId;
    private LocalDateTime createdAt;
    private String content;

    public MessageDTO() {}

    public MessageDTO(Long messageId, LocalDateTime createdAt, String content) {
        this.messageId = messageId;
        this.createdAt = createdAt;
        this.content = content;
    }

    public Long getMessageId() {
        return this.messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
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


    public String toJson() {
        return GsonFormatter.GSON.toJson(this, MessageDTO.class);
    }
}
