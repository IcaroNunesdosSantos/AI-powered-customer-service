package com.empresa.atendimento.conversation.api.dto;

import com.empresa.atendimento.conversation.domain.Conversation;
import com.empresa.atendimento.conversation.domain.ConversationStatus;

import java.util.UUID;

public record ConversationResponse(
    UUID id,
    ConversationStatus status,
    String customerId,
    String agentId
) {
    public static ConversationResponse from(Conversation conversation) {
        return new ConversationResponse(
            conversation.getId(),
            conversation.getStatus(),
            conversation.getCustomerId(),
            conversation.getAgentId()
        );
    }
}
