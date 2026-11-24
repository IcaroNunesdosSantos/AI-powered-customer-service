package com.empresa.atendimento.ai.application;

import com.empresa.atendimento.conversation.domain.Conversation;
import com.empresa.atendimento.conversation.domain.ConversationMessage;
import com.empresa.atendimento.conversation.infra.ConversationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContextBuilder {

    private final ConversationRepository conversationRepository;

    public ContextBuilder(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public String build(String conversationId) {
        Conversation conversation = conversationRepository.findById(UUID.fromString(conversationId))
            .orElseThrow(() -> new IllegalArgumentException("Conversation not found: " + conversationId));

        StringBuilder context = new StringBuilder();
        context.append("Conversation ID: ").append(conversationId).append("\n");
        context.append("Customer ID: ").append(conversation.getCustomerId()).append("\n");
        context.append("Status: ").append(conversation.getStatus()).append("\n\n");
        context.append("Message History:\n");

        for (ConversationMessage message : conversation.getMessages()) {
            context.append(message.getRole()).append(": ").append(message.getContent()).append("\n");
        }

        return context.toString();
    }
}
