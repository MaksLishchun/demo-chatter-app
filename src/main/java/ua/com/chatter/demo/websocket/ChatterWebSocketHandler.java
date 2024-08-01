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
import ua.com.chatter.demo.model.dto.message.MessageDTO;
import ua.com.chatter.demo.model.dto.request.MessageRequest;
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
        sessions.add(session);
        objectMapper.registerModule(new JavaTimeModule());
        log.info("Registered connection");
    }

    @Override
    public void handleMessage(@NonNull WebSocketSession session, @NonNull WebSocketMessage<?> message) throws Exception {
        MessageRequest chatterMessage = objectMapper.readValue(message.getPayload().toString(), MessageRequest.class);

        keysMap.put(session.getId(), chatterMessage.getChatId());

        log.info("ChatWebSocket handleMessage called");
        MessageDTO savedMessage = messagessService.saveMessage(chatterMessage);
        
        for (WebSocketSession elem : sessions) {
            Long elemChatId = keysMap.get(elem.getId());
            if (elemChatId != null && elem.isOpen() && elemChatId.equals(chatterMessage.getChatId())) {
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
