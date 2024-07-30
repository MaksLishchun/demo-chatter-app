package ua.com.chatter.demo.model.dto.message;

import java.time.LocalDateTime;

import ua.com.chatter.demo.utils.GsonFormatter;

public class MessageDTO {

    private Long messageId;
    private LocalDateTime createdAt;
    private String content;
    private Boolean edited;

    public MessageDTO() {}


    public MessageDTO(Long messageId, LocalDateTime createdAt, String content, Boolean edited) {
        this.messageId = messageId;
        this.createdAt = createdAt;
        this.content = content;
        this.edited = edited;
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


    public Boolean isEdited() {
        return this.edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }


    public String toJson() {
        return GsonFormatter.GSON.toJson(this, MessageDTO.class);
    }
}
