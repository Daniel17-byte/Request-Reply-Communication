package org.sd.chat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sd.chat.model.ChatMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New connection established: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

        try {
            ChatMessage incomingMessage = objectMapper.readValue(payload, ChatMessage.class);

            ChatMessage outgoingMessage = new ChatMessage();
            outgoingMessage.setUsername(incomingMessage.getUsername());
            outgoingMessage.setMessage(incomingMessage.getMessage());

            String jsonMessage = objectMapper.writeValueAsString(outgoingMessage);

            for (WebSocketSession s : sessions) {
                if (s.isOpen()) {
                    s.sendMessage(new TextMessage(jsonMessage));
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to parse message: " + e.getMessage());
            session.sendMessage(new TextMessage("{\"error\": \"Invalid message format\"}"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }

}