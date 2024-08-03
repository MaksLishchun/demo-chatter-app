package ua.com.chatter.demo.model.dto.chat;

import java.time.LocalDateTime;

import javax.annotation.Nullable;

import ua.com.chatter.demo.model.ChatType;

public class ChatDTO {
    private Long chatId;
    private String chatName;
    private @Nullable String chatLogo;
    private ChatType type;
    private LocalDateTime lastMessageTime;
    private Long createdBy;


    public ChatDTO() {
    }


    public ChatDTO(Long chatId, String chatName, String chatLogo, ChatType type, LocalDateTime lastMessageTime, Long createdBy) {
        this.chatId = chatId;
        this.chatName = chatName;
        this.chatLogo = chatLogo;
        this.type = type;
        this.lastMessageTime = lastMessageTime;
        this.createdBy = createdBy;
    }


    public Long getChatId() {
        return this.chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
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
