package com.empresa.atendimento.conversation.api.dto;

import java.util.UUID;

public record SendMessageRequest(
    UUID conversationId,
    String content
) {}
