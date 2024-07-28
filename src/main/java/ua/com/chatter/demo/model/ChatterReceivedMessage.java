package ua.com.chatter.demo.model;

import java.time.LocalDateTime;

import ua.com.chatter.demo.utils.GsonFormatter;


public class ChatterReceivedMessage {

    private Long ownerId;
    private Long recipientId;
    private LocalDateTime date;
    private String content;

    public ChatterReceivedMessage() {}

    public ChatterReceivedMessage(Long ownerId, Long recipientId, LocalDateTime date, String content) {
        this.ownerId = ownerId;
        this.recipientId = recipientId;
        this.date = date;
        this.content = content;
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

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toJson() {
        return GsonFormatter.GSON.toJson(this, ChatterReceivedMessage.class);
    }

}

