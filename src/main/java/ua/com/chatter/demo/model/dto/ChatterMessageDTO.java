package ua.com.chatter.demo.model.dto;

import java.time.LocalDateTime;

public class ChatterMessageDTO {

    private Long messageId;

    private Long ownerId;
    private Long recipientId;
    private LocalDateTime date;
    private String sender;
    private String content;

    public ChatterMessageDTO() {}

    public ChatterMessageDTO(Long ownerId, Long recipientId, LocalDateTime date, String sender, String content) {
        this.ownerId = ownerId;
        this.recipientId = recipientId;
        this.date = date;
        this.sender = sender;
        this.content = content;
    }


    public Long getMessageId() {
        return this.messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getRecipientId() {
        return this.recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
