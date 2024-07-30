package ua.com.chatter.demo.model.dto;

public class ChatDTO {
    private Long chatId;
    private String chatName;
    private String chatLogo;


    public ChatDTO() {
    }

    public ChatDTO(Long chatId, String chatName, String chatLogo) {
        this.chatId = chatId;
        this.chatName = chatName;
        this.chatLogo = chatLogo;
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

}
