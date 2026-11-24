package com.empresa.atendimento.realtime.websocket;

import com.empresa.atendimento.conversation.api.dto.SendMessageRequest;
import com.empresa.atendimento.conversation.application.ReceiveCustomerMessage;
import com.empresa.atendimento.ai.application.AiOrchestrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    private final ReceiveCustomerMessage receiveCustomerMessage;
    private final AiOrchestrator aiOrchestrator;

    public WebSocketHandler(ReceiveCustomerMessage receiveCustomerMessage,
                            AiOrchestrator aiOrchestrator) {
        this.receiveCustomerMessage = receiveCustomerMessage;
        this.aiOrchestrator = aiOrchestrator;
    }

    @MessageMapping("/message")
    @SendTo("/topic/conversation")
    public Map<String, Object> handleMessage(SendMessageRequest request) {
        log.info("Received WebSocket message for conversation: {}", request.conversationId());

        // Save customer message
        receiveCustomerMessage.execute(request.conversationId(), request.content());

        // Generate AI response
        String aiResponse = aiOrchestrator.processCustomerMessage(
            request.conversationId().toString(),
            request.content()
        );

        return Map.of(
            "conversationId", request.conversationId(),
            "role", "AI",
            "content", aiResponse
        );
    }
}
