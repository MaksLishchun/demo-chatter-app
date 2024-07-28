package ua.com.chatter.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;
import ua.com.chatter.demo.model.ChatterReceivedMessage;

@Slf4j
@Component
public class ChatterWebSocketHandler implements WebSocketHandler {


    private List<WebSocketSession> sessions = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Connection established");
        sessions.add(session);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Object mesageObj = message.getPayload();
        log.info(String.format("Got message is %s", mesageObj.getClass().getName()));

        ChatterReceivedMessage chatterMessage = objectMapper.readValue(message.getPayload().toString(), ChatterReceivedMessage.class);

        log.info(String.format("ownerId = %1s; \nrecipientId = %2s;\ncontent = %3s;", chatterMessage.getOwnerId().toString(), chatterMessage.getRecipientId().toString(), chatterMessage.getContent()));
        
        for (WebSocketSession elem : sessions) {
            if (elem.isOpen()) {
                elem.sendMessage(message);
            }
        }
       
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Connection closed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

