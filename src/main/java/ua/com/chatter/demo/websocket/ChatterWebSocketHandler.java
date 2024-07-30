package ua.com.chatter.demo.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;
import ua.com.chatter.demo.model.dto.MessageDTO;
import ua.com.chatter.demo.model.dto.MessageRequest;
import ua.com.chatter.demo.service.MessagessService;

@Slf4j
@Component
public class ChatterWebSocketHandler implements WebSocketHandler {

    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Long> keysMap = new HashMap<>();

    @Autowired
    private MessagessService messagessService;

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {

        Map<String, Object> attributes = session.getAttributes();
        Long chatId = (Long) attributes.get("chatId");
        
        if (chatId == null) {
            session.close(CloseStatus.NOT_ACCEPTABLE);
            return;
        }

        sessions.add(session);
        keysMap.put(session.getId(), chatId);
        objectMapper.registerModule(new JavaTimeModule());
        log.info("Registered connection");
    }

    @Override
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        Long userId = (Long) attributes.get("userId");
        Long chatId = (Long) attributes.get("chatId");

        Object mesageObj = message.getPayload();
        log.info(String.format("Got message is %s", mesageObj.getClass().getName()));

        MessageRequest chatterMessage = objectMapper.readValue(message.getPayload().toString(), MessageRequest.class);

        MessageDTO savedMessage = messagessService.saveMessage(chatterMessage, userId, chatId);
        
        for (WebSocketSession elem : sessions) {
            Long elemChatId = keysMap.get(elem.getId());
            if (elem.isOpen() && elemChatId.equals(chatId)) {
                elem.sendMessage(new TextMessage(savedMessage.toJson()));
            }
        }

    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) throws Exception {
        session.close();
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
        keysMap.remove(session.getId());
        log.info("Connection closed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}
