package com.empresa.atendimento.conversation.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageSentEvent(
    UUID conversationId,
    UUID messageId,
    MessageRole role,
    String content,
    LocalDateTime sentAt
) {}
