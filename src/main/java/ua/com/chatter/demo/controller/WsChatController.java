package ua.com.chatter.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import ua.com.chatter.demo.model.dto.request.MessageRequest;
import ua.com.chatter.demo.model.entity.ChatEntity;
import ua.com.chatter.demo.model.entity.UserEntity;
import ua.com.chatter.demo.service.ChatService;

@Controller
@Slf4j
public class WsChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat")
    @SendTo("/topic/public")
    public void sendMessage(@Payload MessageRequest chatMessage) {
        log.info("ChatWebSocket sendMessage called");
        ChatEntity chat = chatService.getChat(chatMessage.getChatId());
        for (UserEntity user : chat.getUsers()) {
            messagingTemplate.convertAndSendToUser(user.getId().toString(), "/queue/messages", chatMessage);
        }
    }
}
