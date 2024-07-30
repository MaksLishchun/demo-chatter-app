package ua.com.chatter.demo.model.dto.chat;

import java.util.List;

import ua.com.chatter.demo.model.ChatType;

public class ChatCreateRequest {
    private String chatName;
    private Long createdBy;
    private ChatType type;
    private List<Long> ids;


    public ChatCreateRequest(String chatName, Long createdBy, ChatType type, List<Long> ids) {
        this.chatName = chatName;
        this.createdBy = createdBy;
        this.type = type;
        this.ids = ids;
    }


    public String getChatName() {
        return this.chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public ChatType getType() {
        return this.type;
    }

    public void setType(ChatType type) {
        this.type = type;
    }

    public List<Long> getIds() {
        return this.ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

}
