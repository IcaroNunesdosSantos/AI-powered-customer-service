package com.empresa.atendimento.realtime.publisher;

import com.empresa.atendimento.conversation.domain.ConversationCreatedEvent;
import com.empresa.atendimento.conversation.domain.MessageSentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessagePublisher {

    private static final Logger log = LoggerFactory.getLogger(MessagePublisher.class);

    private final SimpMessagingTemplate messagingTemplate;

    public MessagePublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleConversationCreated(ConversationCreatedEvent event) {
        log.info("Publishing conversation created event: {}", event.conversationId());
        messagingTemplate.convertAndSend(
            "/topic/conversations/" + event.conversationId(),
            Map.of(
                "type", "CONVERSATION_CREATED",
                "conversationId", event.conversationId(),
                "customerId", event.customerId()
            )
        );
    }

    @EventListener
    public void handleMessageSent(MessageSentEvent event) {
        log.info("Publishing message sent event: {}", event.messageId());
        messagingTemplate.convertAndSend(
            "/topic/conversations/" + event.conversationId(),
            Map.of(
                "type", "MESSAGE_SENT",
                "conversationId", event.conversationId(),
                "messageId", event.messageId(),
                "role", event.role().toString(),
                "content", event.content()
            )
        );
    }
}
