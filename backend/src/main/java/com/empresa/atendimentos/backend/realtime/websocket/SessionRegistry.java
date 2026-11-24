package com.empresa.atendimento.realtime.websocket;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionRegistry {

    private final Map<UUID, String> sessionMap = new ConcurrentHashMap<>();

    public void register(UUID conversationId, String sessionId) {
        sessionMap.put(conversationId, sessionId);
    }

    public void unregister(UUID conversationId) {
        sessionMap.remove(conversationId);
    }

    public String getSessionId(UUID conversationId) {
        return sessionMap.get(conversationId);
    }

    public boolean isRegistered(UUID conversationId) {
        return sessionMap.containsKey(conversationId);
    }
}
