package ua.com.chatter.demo.model.dto.request;

import javax.annotation.Nullable;


public class MessageRequest {

    private @Nullable Long id;
    private Long userId;
    private Long chatId;
    private String content;

    public MessageRequest() {}

    public MessageRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
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
