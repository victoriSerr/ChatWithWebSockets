package ru.itis.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.sockjs.transport.session.WebSocketServerSockJsSession;
import ru.itis.dto.MessageDto;

import java.util.*;

@Component
public class WebSocketMessageHandler extends TextWebSocketHandler {

    private static final Map<String, WebSocketSession> sessions = new HashMap<>();
    private static final Map<String, Map<String, WebSocketSession>> rooms = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        String messageText = (String) message.getPayload();
        MessageDto messageFromJson = objectMapper.readValue(messageText, MessageDto.class);

        String roomId = messageFromJson.getRoomId();
        Map<String, WebSocketSession> map = new HashMap<>();

        if (rooms.containsKey(roomId)) {
            map = rooms.get(roomId);
        }

        map.put(messageFromJson.getLogin(), session);
        rooms.put(roomId, map);

        for (WebSocketSession currentSession : map.values()) {
            currentSession.sendMessage(message);
        }
    }

    public Set<String> getRoomsId() {
        return rooms.keySet();
    }
}
