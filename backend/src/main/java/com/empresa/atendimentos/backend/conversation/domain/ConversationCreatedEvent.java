package com.empresa.atendimento.conversation.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConversationCreatedEvent(
    UUID conversationId,
    String customerId,
    LocalDateTime createdAt
) {}
