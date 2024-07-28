package ua.com.chatter.demo.websocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
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

    private final List<WebSocketSession> sessions = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, String> keysMap = new HashMap();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Map<String, Object> attributes = session.getAttributes();
        String key = (String) attributes.get("key");

        log.info("Connected: " + session.getId() + " with key: " + key);
        
        if (!isValidKey(key)) {
            session.close(CloseStatus.NOT_ACCEPTABLE);
            return;
        }

        log.info("Connection established");

        log.info("ChatterConntection --- size before = " + sessions.size());


        if (sessions.size() > 1) {
            Set<WebSocketSession> duplicates = findDuplicates(sessions);
            sessions.removeAll(duplicates);
        }

        log.info("ChatterConntection --- size after = " + sessions.size());

        sessions.add(session);
        keysMap.put(session.getId(), key);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        Map<String, Object> attributes = session.getAttributes();
        String currentChatKey = (String) attributes.get("key");

        Object mesageObj = message.getPayload();
        log.info(String.format("Got message is %s", mesageObj.getClass().getName()));

        ChatterReceivedMessage chatterMessage = objectMapper.readValue(message.getPayload().toString(), ChatterReceivedMessage.class);

        for (WebSocketSession elem : sessions) {
            String chatKey = keysMap.get(elem.getId());
            if (elem.isOpen() && chatKey.equals(currentChatKey)) {
                elem.sendMessage(new TextMessage(chatterMessage.toJson()));
            }
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        session.close();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        sessions.remove(session);
        keysMap.remove(session.getId());
        log.info("Connection closed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private boolean isValidKey(String key) {
        return key != null && !key.isEmpty();
    }

    public Set<WebSocketSession> findDuplicates(List<WebSocketSession> listContainingDuplicates) {
        final Set<WebSocketSession> setToReturn = new HashSet<>();
        final Set<WebSocketSession> set1 = new HashSet<>();

        for (int index = 0; index < listContainingDuplicates.size() - 1; index++) {
            WebSocketSession item = listContainingDuplicates.get(index);
            if (!set1.add(item)) {
                setToReturn.add(item);
            }
        } 
        return setToReturn;
    }
}
